package com.example.avi_pc.weatherdemo.database;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.avi_pc.weatherdemo.Constants;
import com.example.avi_pc.weatherdemo.injection.ApplicationContext;
import com.example.avi_pc.weatherdemo.model.Weather;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class TemperatureTable {
    private Context context;

    @Inject
    TemperatureTable(@ApplicationContext Context context) {
        this.context = context;
    }

    public int createOrUpdateUser(List<Weather> weatherList) {
        Dao.CreateOrUpdateStatus status = null;
        int count = -1;
        try {
            for (Weather weather : weatherList) {
                status = DatabaseHelper.getInstance(context).getTemperatureDao().createOrUpdate(weather);
            }
        } catch (SQLException e) {
            Log.d(TemperatureTable.class.getSimpleName(), e.toString());
        }
        if (status != null) count = status.getNumLinesChanged();
        return count;
    }

    @SuppressLint("UseValueOf")
    public List<Weather> getTemperatureList(String year, String country) {
        List<Weather> weatherList = null;
        try {
            QueryBuilder<Weather, Integer> queryBuilder = DatabaseHelper.getInstance(context).getTemperatureDao().queryBuilder();
            queryBuilder.where().eq(Constants.YEAR, year).and().eq(Constants.CONTRY, country);
            weatherList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weatherList;
    }


    @SuppressLint("UseValueOf")
    public List<String> getYears(String country) {
        List<String> years = null;
        List<Weather> weatherList = null;
        try {

            QueryBuilder<Weather, Integer> queryBuilder = DatabaseHelper.getInstance(context).getTemperatureDao().queryBuilder();
            queryBuilder.where().eq(Constants.CONTRY, country);
            weatherList = queryBuilder.distinct().selectColumns("year").query();
            years = new ArrayList<>();
            for (Weather weather : weatherList) {
                years.add(weather.getYear());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return years;
    }


    public List<Weather> getWeatherData(String country) {
        List<Weather> weatherList = null;
        try {
            QueryBuilder<Weather, Integer> queryBuilder = DatabaseHelper.getInstance(context).getTemperatureDao().queryBuilder();
            queryBuilder.where().eq(Constants.CONTRY,  country);
            queryBuilder.limit(1L);
            weatherList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weatherList;
    }
}

