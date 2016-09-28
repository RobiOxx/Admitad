package com.admitad.android.mvp.presenter;


import com.admitad.android.api.ApiInterface;
import com.admitad.android.data.models.ModelLogin;
import com.admitad.android.mvp.view.BaseView;
import com.admitad.android.mvp.view.LoginView;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements BaseView {

    private LoginView mLoginView;

    public LoginPresenter(LoginView loginView) {
        mLoginView = loginView;
    }

    public void getToken(ApiInterface apiInterface, Map<String, String> data) {
        Call<ModelLogin> getToken = apiInterface.getUserToken(data);
        getToken.enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                if (mLoginView != null) mLoginView.successAuthorization(response.body());
            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {
                if (mLoginView != null) mLoginView.errorAuthorization();
            }
        });
    }

    @Override
    public void onDestroy() {
        mLoginView = null;
    }

}
