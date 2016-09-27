package com.admitad.android.mvp.view;


import com.admitad.android.data.models.ModelBalance;

public interface MainView {
    void setBalance(ModelBalance modelBalance);
    void onError();
}
