package com.admitad.android.system;


import android.content.Context;
import android.content.SharedPreferences;

import com.admitad.android.data.models.ModelLogin;
import com.google.gson.Gson;

import javax.inject.Inject;

public class SharedPref {

    private SharedPreferences sharedPref;
    private Gson gson;

    private static final String PREF_NAME = "com.admitad.android";
    private static final String FIELD_USER_INFO = "user_info";

    @Inject
    public SharedPref(Context activity) {
        sharedPref = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public ModelLogin getUserInfo() {
        String json = sharedPref.getString(FIELD_USER_INFO, null);
        if (json == null) return null;
        else {
            return gson.fromJson(json, ModelLogin.class);
        }
    }

    public void setUserInfo(ModelLogin data) {
        SharedPreferences.Editor editor = sharedPref.edit();
        String user_info = gson.toJson(data);
        editor.putString(FIELD_USER_INFO, user_info);
        editor.apply();
    }

    public void deleteUserInfo() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(FIELD_USER_INFO);
        editor.apply();
    }

}
