package com.admitad.android.mvp.view;


import com.admitad.android.data.models.ModelLogin;

public interface LoginView {

    void successAuthorization(ModelLogin data);
    void errorAuthorization();

}
