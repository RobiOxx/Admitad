package com.admitad.android.data.models;


import java.util.List;

public class ModelBalance {

    private List<ModelBalanceItem> mModelBalanceItems;

    public ModelBalance() {
    }

    public List<ModelBalanceItem> getModelBalanceItems() {
        return mModelBalanceItems;
    }

    public void setModelBalanceItems(List<ModelBalanceItem> modelBalanceItems) {
        mModelBalanceItems = modelBalanceItems;
    }

}
