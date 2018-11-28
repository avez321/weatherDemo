package com.example.avi_pc.weatherdemo.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Data implements Serializable {
    @JsonProperty("value")
    float value;
    @JsonProperty("year")
    String year;
    @JsonProperty("month")
    String month;

    public Data() {

    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
