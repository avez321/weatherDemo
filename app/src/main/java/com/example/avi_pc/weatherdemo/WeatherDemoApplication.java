package com.example.avi_pc.weatherdemo;

import android.app.Application;
import android.content.Context;

import com.example.avi_pc.weatherdemo.injection.component.ApplicationComponent;
import com.example.avi_pc.weatherdemo.injection.component.DaggerApplicationComponent;
import com.example.avi_pc.weatherdemo.injection.module.ApplicationModule;


public class WeatherDemoApplication extends Application  {
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
            mApplicationComponent.inject(this);
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static WeatherDemoApplication get(Context context) {
        return (WeatherDemoApplication) context.getApplicationContext();
    }
}

