package com.example.avi_pc.weatherdemo.injection.module;

import android.app.Application;
import android.content.Context;


import com.example.avi_pc.weatherdemo.injection.ApplicationContext;
import com.example.avi_pc.weatherdemo.remote.RetrofitFactory;
import com.example.avi_pc.weatherdemo.remote.WeatherDemoAppApi;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


@Module
public class ApplicationModule {
    private Application mApplication;

    public ApplicationModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit() {
        return RetrofitFactory.getRetrofit(mApplication);
    }

    @Singleton
    @Provides
    WeatherDemoAppApi providesMobilManiApiService(Retrofit retrofit) {
        return retrofit.create(WeatherDemoAppApi.class);
    }

}