package com.example.avi_pc.weatherdemo.injection.component;



import com.example.avi_pc.weatherdemo.activity.home.HomeActivity;
import com.example.avi_pc.weatherdemo.injection.PerActivity;
import com.example.avi_pc.weatherdemo.injection.module.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(HomeActivity homeActivity);

}