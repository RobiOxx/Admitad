package com.admitad.android.data.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelBalanceItem {

    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("balance")
    @Expose
    private String balance;

    public ModelBalanceItem() {
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
