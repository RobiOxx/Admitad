package com.admitad.android.mvp.presenter;


import android.util.Log;

import com.admitad.android.api.ApiInterface;
import com.admitad.android.data.models.ModelBalance;
import com.admitad.android.mvp.view.BaseView;
import com.admitad.android.mvp.view.MainView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements BaseView {

    private MainView mMainView;

    public MainPresenter(MainView mainView) {
        mMainView = mainView;
    }

    public void getBalance(String token, ApiInterface apiInterface) {
        Call<List<ModelBalance>> getBalance = apiInterface.getUserBalance(token);
        getBalance.enqueue(new Callback<List<ModelBalance>>() {
            @Override
            public void onResponse(Call<List<ModelBalance>> call, Response<List<ModelBalance>> response) {
                if (mMainView != null) mMainView.setBalance(response.body());
            }

            @Override
            public void onFailure(Call<List<ModelBalance>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
                if (mMainView != null) mMainView.onError();
            }
        });
    }


    @Override
    public void onDestroy() {
        mMainView = null;
    }
}
