package com.admitad.android.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.admitad.android.App;
import com.admitad.android.api.ApiClient;
import com.admitad.android.api.ApiInterface;
import com.admitad.android.system.FragmentSystem;
import com.admitad.android.system.SharedPref;

import javax.inject.Inject;

public class ActivityBase extends AppCompatActivity {

    @Inject public SharedPref mPref;
    @Inject public FragmentSystem frSystem;

    public ApiInterface mApiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getNetComponent(this).inject(this);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void showSnackbar(View view, String messages) {
        Snackbar snackbar = Snackbar.make(view, messages, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
