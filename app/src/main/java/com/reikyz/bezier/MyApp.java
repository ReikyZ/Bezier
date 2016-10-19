package com.reikyz.bezier;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

/**
 * Created by reikyZ on 2016/10/15.
 */

public class MyApp extends Application {
    final static String TAG = "===MyApp===";


    static MyApp instance;
    static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


    public synchronized static MyApp getInstance() {
        if (instance == null) {
            instance = new MyApp();
        }
        return instance;
    }

    public static Context getContext() {
        return context;
    }
}
