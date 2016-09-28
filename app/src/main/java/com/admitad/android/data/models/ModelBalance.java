package com.admitad.android.data.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelBalance implements Parcelable {

    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("balance")
    @Expose
    private String balance;

    public ModelBalance(String currency, String balance) {
        this.currency = currency;
        this.balance = balance;
    }

    public ModelBalance() { }

    public ModelBalance(Parcel in) {
        currency = in.readString();
        balance = in.readString();
    }

    public static final Creator<ModelBalance> CREATOR = new Creator<ModelBalance>() {
        @Override
        public ModelBalance createFromParcel(Parcel in) {
            return new ModelBalance(in);
        }

        @Override
        public ModelBalance[] newArray(int size) {
            return new ModelBalance[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(currency);
        parcel.writeString(balance);
    }

}
