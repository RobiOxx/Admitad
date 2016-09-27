package com.admitad.android.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.admitad.android.R;
import com.admitad.android.api.ApiClient;
import com.admitad.android.data.models.ModelLogin;
import com.admitad.android.mvp.presenter.LoginPresenter;
import com.admitad.android.mvp.view.LoginView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ActivityLogin extends ActivityBase implements LoginView {

    @BindView(R.id.login_main_layout) RelativeLayout loginMainLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.login_layout) RelativeLayout loginLayout;
    @BindView(R.id.login_webview) WebView loginWeb;
    @BindView(R.id.login_progress) ProgressBar loginProgress;

    private LoginPresenter mLoginPresenter;
    private Unbinder mUnbinder;

    private long requestTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenter(this);

        setSupportActionBar(mToolbar);

        loginWeb.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                errorAuthorization();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (loginWeb.getVisibility() == View.GONE) {
                    loginProgress.setVisibility(View.GONE);
                    loginWeb.setVisibility(View.VISIBLE);
                }
                if (url.contains(ApiClient.login_state + "&code=")) {
                    String[] code_split = url.split("&code=");
                    String code = code_split[1];
                    loginProgress.setVisibility(View.VISIBLE);
                    loginWeb.setVisibility(View.GONE);

                    Map<String, String> map = new HashMap<>();
                    map.put("client_id", ApiClient.login_app_id);
                    map.put("client_secret", ApiClient.login_secret);
                    map.put("grant_type", "authorization_code");
                    map.put("code", code);
                    map.put("redirect_uri", "https://www.google.ru/");

                    requestTime = System.currentTimeMillis();

                    mLoginPresenter.getToken(mApiInterface, map);
                }
            }
        });
        loginWeb.getSettings().setJavaScriptEnabled(true);
    }

    @OnClick(R.id.login_button)
    void onAuthClick() {
        loginLayout.setVisibility(View.GONE);
        loginProgress.setVisibility(View.VISIBLE);
        loginWeb.loadUrl(ApiClient.LOGIN_CODE);
    }

    @Override
    public void successAuthorization(ModelLogin data) {
        data.setLoginDate(requestTime == 0 ? System.currentTimeMillis() : requestTime);
        mPref.setUserInfo(data);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void errorAuthorization() {
        showSnackbar(loginMainLayout, getString(R.string.msg_login_error));
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (mLoginPresenter != null) {
            mLoginPresenter.onDestroy();
        }
    }
}
