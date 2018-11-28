package com.example.avi_pc.weatherdemo.injection.component;

import android.app.Application;
import android.content.Context;


import com.example.avi_pc.weatherdemo.WeatherDemoApplication;
import com.example.avi_pc.weatherdemo.database.TemperatureTable;
import com.example.avi_pc.weatherdemo.injection.ApplicationContext;
import com.example.avi_pc.weatherdemo.injection.module.ApplicationModule;
import com.example.avi_pc.weatherdemo.remote.WeatherDemoAppApi;


import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(WeatherDemoApplication weatherDemoApplication);

    @ApplicationContext
    Context context();

    Application application();

    WeatherDemoAppApi whetherDemoAppApi();

    TemperatureTable temperatureTable();

}