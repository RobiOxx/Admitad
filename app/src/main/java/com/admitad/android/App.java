package com.admitad.android;


import android.app.Application;
import android.content.Context;

public class App extends Application {

    private NetComponent mNetComponent;

    protected AppModule getApplicationModel() {
        return new AppModule(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static NetComponent getNetComponent(Context context) {
        App app = (App) context.getApplicationContext();
        if (app.mNetComponent == null) {
            app.mNetComponent = DaggerNetComponent.builder()
                    .appModule(app.getApplicationModel())
                    .build();
        }

        return app.mNetComponent;
    }

}
