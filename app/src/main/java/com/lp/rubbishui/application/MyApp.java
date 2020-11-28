package com.lp.rubbishui.application;

import android.app.Application;

public class MyApp extends Application {
    private static MyApp yApp;

    @Override
    public void onCreate() {
        super.onCreate();
        yApp = this;
    }

    public static MyApp app() {
        return yApp;
    }
}
