package com.zed.simple;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class SimpleApplication extends MultiDexApplication {
    static SimpleApplication application;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static SimpleApplication getContext() {
        return application;
    }
}
