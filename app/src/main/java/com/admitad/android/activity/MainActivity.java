package com.admitad.android.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.admitad.android.R;
import com.admitad.android.data.models.ModelBalance;
import com.admitad.android.data.models.ModelLogin;
import com.admitad.android.mvp.presenter.MainPresenter;
import com.admitad.android.mvp.view.MainView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends ActivityBase
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    private MainPresenter mMainPresenter;
    private Unbinder mUnbinder;
    private IntentFilter mIntentFilter;

    private int current_id = R.id.nav_main;

    public static final String mBroadcastStringAction = "com.admitad.android.string";
    private static final int ACTIVITY_LOGIN_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        mMainPresenter = new MainPresenter(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        checkLogin();
    }

    @UiThread
    private void checkLogin() {
        if (mPref.getUserInfo() == null) {
            startActivityForResult(new Intent(this, ActivityLogin.class), ACTIVITY_LOGIN_CODE);
        } else {
            ModelLogin modelLogin = mPref.getUserInfo();
            long time = TimeUnit.SECONDS.toMillis(modelLogin.getLoginDate());
            if (System.currentTimeMillis() >= time + modelLogin.getExpiresIn()) {
                mPref.deleteUserInfo();
                checkLogin();
            } else {
                mIntentFilter = new IntentFilter();
                mIntentFilter.addAction(mBroadcastStringAction);

                //FragmentSystem.getInstance().openFragment(getSupportFragmentManager(),
                //        FragmentSystem.FragmentEnum.FragmentMain, R.id.container, false);
                String token = "Bearer " + mPref.getUserInfo().getAccessToken();
                mMainPresenter.getBalance(token, mApiInterface);
            }
        }
    }

    @Override
    public void setBalance(List<ModelBalance> list) {
        ModelLogin modelLogin = mPref.getUserInfo();

        View headerLayout = navigationView.getHeaderView(0);

        TextView username = (TextView) headerLayout.findViewById(R.id.nav_header_username);
        TextView lang = (TextView) headerLayout.findViewById(R.id.nav_header_lang);
        TextView rub = (TextView) headerLayout.findViewById(R.id.nav_header_balance_rub);
        TextView usd = (TextView) headerLayout.findViewById(R.id.nav_header_balance_usd);
        TextView eur = (TextView) headerLayout.findViewById(R.id.nav_header_balance_euro);

        if (modelLogin.getUsername() != null) {
            username.setText(modelLogin.getUsername());
        }
        if (modelLogin.getLanguage() != null) {
            lang.setText(modelLogin.getLanguage());
        }

        for (ModelBalance balanceItem : list) {
            String currency = balanceItem.getCurrency();
            String balance = balanceItem.getBalance();
            if (currency.contains("RUB")) {
                rub.setText(String.format(getString(R.string.nav_drawer_balance_balance),
                        currency, balance));
            } else if (currency.contains("EUR")) {
                usd.setText(String.format(getString(R.string.nav_drawer_balance_balance),
                        currency, balance));
            } else if (currency.contains("USD")) {
                eur.setText(String.format(getString(R.string.nav_drawer_balance_balance),
                        currency, balance));
            }
        }

        String rub_text = rub.getText().toString();
        String usd_text = usd.getText().toString();
        String eur_text = eur.getText().toString();

        if (rub_text.isEmpty() || rub_text.equals("") || rub_text.contains("0.00")) {
            rub.setText(String.format(getString(R.string.nav_drawer_balance_empty), "RUB"));
        }
        if (usd_text.isEmpty() || usd_text.equals("") || usd_text.contains("0.00")) {
            usd.setText(String.format(getString(R.string.nav_drawer_balance_empty), "USD"));
        }
        if (eur_text.isEmpty() || eur_text.equals("") || eur_text.contains("0.00")) {
            eur.setText(String.format(getString(R.string.nav_drawer_balance_empty), "EUR"));
        }
    }

    @Override
    public void onError() {

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id != current_id) {

            if (id == R.id.nav_main) {

            } else if (id == R.id.nav_tools) {

            } else if (id == R.id.nav_programmes) {

            } else if (id == R.id.nav_goods) {

            } else if (id == R.id.nav_stats) {

            } else if (id == R.id.nav_app) {

            } else if (id == R.id.nav_settings) {

            } else if (id == R.id.nav_exit) {

            }

            current_id = id;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ACTIVITY_LOGIN_CODE) {
                checkLogin();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (mMainPresenter != null) {
            mMainPresenter.onDestroy();
        }
    }
}
