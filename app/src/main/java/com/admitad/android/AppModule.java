package com.admitad.android;


import android.app.Application;

import com.admitad.android.system.FragmentSystem;
import com.admitad.android.system.SharedPref;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public SharedPref providePref() {
        return new SharedPref(app.getApplicationContext());
    }

    @Provides
    @Singleton
    public Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttp(Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(cache);
        client.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        client.readTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        return client.build();
    }

    @Provides
    @Singleton
    public FragmentSystem provideFragmentSystem() {
        return new FragmentSystem();
    }

}
