package com.admitad.android.api;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private final static String login_scope = "public_data%20websites%20manage_websites%20advcampaigns%20advcampaigns_for_website%20manage_advcampaigns%20banners%20landings%20arecords%20banners_for_website%20payments%20manage_payments%20announcements%20referrals%20coupons%20coupons_for_website%20private_data%20tickets%20manage_tickets%20private_data_balance%20validate_links%20deeplink_generator%20statistics%20opt_codes%20manage_opt_codes%20lost_orders%20manage_lost_orders";
    private final static String login_redirect = "https://www.google.ru/";
    public final static String login_secret = "e22afe807f0e4a8aecc2c21540f856";
    public final static String login_app_id = "75166ef32acc24097b7a22c88b4fa4";
    public final static String login_state = "7c232ff20e64432fbe071228c0779f";
    final static String login_base64 = "NzUxNjZlZjMyYWNjMjQwOTdiN2EyMmM4OGI0ZmE0OmUyMmFmZTgwN2YwZTRhOGFlY2MyYzIxNTQwZjg1Ng==";


    private static final String BASE_URL = "https://api.admitad.com/";
    public static final String LOGIN_CODE =
            "https://api.admitad.com/authorize/?scope=" + login_scope
                    + "&state=" + login_state
                    + "&redirect_uri=" + login_redirect
                    + "&response_type=code&client_id=" + login_app_id;

    private static OkHttpClient client = null;
    private static Retrofit retrofit = null;

    public static synchronized Retrofit getClient() {
        if (client == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(20, TimeUnit.SECONDS);
            httpClient.writeTimeout(20, TimeUnit.SECONDS);
            httpClient.readTimeout(20, TimeUnit.SECONDS);
            httpClient.addInterceptor(logging);

            client = httpClient.build();
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }

}
