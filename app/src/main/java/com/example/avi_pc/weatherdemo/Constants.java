package com.example.avi_pc.weatherdemo;

public class Constants {
    public static final String BASE_URL = "https://s3.eu-west-2.amazonaws.com";
    public static final String API_ERROR_RESPONSE_KEY = "message";
    public static final String TMIN = "Tmin";
    public static final String TMAX = "Tmax";
    public static final String YEAR = "year";
    public static final String RAINFALL ="Rainfall" ;
    public static final String CONTRY ="country" ;
    public static final String[] TEMPERATURE_LABELS = new String[]{
            "Max Temperature", "Min Temperature"
    } ;
    public static final String ERROR_TAG = "***error";
    public static final float TEXT_SIZE =9f ;
    public static final float MINIMUM_AXIS = 0f;
    public static final float MAXIMUM_AXIS = 12f;
    public static final int LABEL_COUNT =12 ;
    public static final float FORM_SIZE = 8f;
    public static final float TEXT_SPACE = 4f;
    public static final float ENTRY_SPACE = 6f;
}
