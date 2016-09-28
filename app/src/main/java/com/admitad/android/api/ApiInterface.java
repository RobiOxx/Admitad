package com.admitad.android.api;


import com.admitad.android.data.models.ModelBalance;
import com.admitad.android.data.models.ModelLogin;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Authorization: Basic " + ApiClient.login_base64
    })
    @FormUrlEncoded
    @POST("token/")
    Call<ModelLogin> getUserToken(@FieldMap Map<String, String> fields);

    @Headers({
            "Content-Type: application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    @POST("token/")
    Call<ModelLogin> updateUserToken(@FieldMap Map<String, String> fields);

    @GET("me/balance/")
    Call<List<ModelBalance>> getUserBalance(@Header("Authorization") String token);


}
