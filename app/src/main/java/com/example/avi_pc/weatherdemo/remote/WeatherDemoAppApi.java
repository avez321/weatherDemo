package com.example.avi_pc.weatherdemo.remote;


import com.example.avi_pc.weatherdemo.model.Data;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface WeatherDemoAppApi {
    @GET("interview-question-data/metoffice/{metric}-{country}.json")
    Observable<List<Data>> getWhetherData(@Path("metric") String metric, @Path("country") String country);
}