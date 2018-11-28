package com.example.avi_pc.weatherdemo.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.avi_pc.weatherdemo.model.Weather;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static DatabaseHelper mInstance;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WeatherDb";

    private Dao<Weather, Integer> mTemperatureDao;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = OpenHelperManager.getHelper(context, DatabaseHelper.class);
            mInstance.createTablesIfNotExists();
        }
        return mInstance;
    }

    public Dao<Weather, Integer> getTemperatureDao() throws SQLException {
        if (mTemperatureDao == null) {
            mTemperatureDao = getDao(Weather.class);
        }
        return mTemperatureDao;
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        createTablesIfNotExists();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,
                          int newVersion) {

    }

    public void createTablesIfNotExists() {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Weather.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTable() {
        try {
            TableUtils.dropTable(connectionSource, Weather.class, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

