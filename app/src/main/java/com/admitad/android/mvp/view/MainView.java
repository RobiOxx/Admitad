package com.admitad.android.mvp.view;


import com.admitad.android.data.models.ModelBalance;

import java.util.List;

public interface MainView {
    void setBalance(List<ModelBalance> list);
    void onError();
}
