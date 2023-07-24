package com.example.kyapplication;

import android.app.Application;

public class App extends Application {

    public static Application app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
