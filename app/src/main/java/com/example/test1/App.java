package com.example.test1;

import android.app.Application;
import android.content.SharedPreferences;

public class App extends Application {
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static App getInstance() {
        return sInstance;
    }

}
