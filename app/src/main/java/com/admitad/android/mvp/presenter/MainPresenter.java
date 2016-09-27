package com.admitad.android.mvp.presenter;


import com.admitad.android.api.ApiInterface;
import com.admitad.android.data.models.ModelBalance;
import com.admitad.android.mvp.view.BaseView;
import com.admitad.android.mvp.view.MainView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements BaseView {

    private MainView mMainView;

    public MainPresenter(MainView mainView) {
        mMainView = mainView;
    }

    public void getBalance(String token, ApiInterface apiInterface) {
        Call<ModelBalance> getBalance = apiInterface.getUserBalance(token);
        getBalance.enqueue(new Callback<ModelBalance>() {
            @Override
            public void onResponse(Call<ModelBalance> call, Response<ModelBalance> response) {
                mMainView.setBalance(response.body());
            }

            @Override
            public void onFailure(Call<ModelBalance> call, Throwable t) {
                mMainView.onError();
            }
        });
    }


    @Override
    public void onDestroy() {
        mMainView = null;
    }
}
