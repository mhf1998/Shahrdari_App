package com.example.mayorapp;

import android.app.Application;

import com.example.mayorapp.DI.Components.AppComponent;
import com.example.mayorapp.DI.Components.DaggerAppComponent;


public class BaseApplication extends Application {

    private AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        appComponent= DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}

