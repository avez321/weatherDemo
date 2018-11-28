package com.example.avi_pc.weatherdemo.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class Weather {
    @DatabaseField(generatedId = true, canBeNull = false, columnName = "id")
    public int id;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private Data maxTemperature;
    @DatabaseField (dataType = DataType.SERIALIZABLE)
    private Data minTemperature;
    @DatabaseField (dataType = DataType.SERIALIZABLE)
    private Data rainfall;
    @DatabaseField
    private String year;
    @DatabaseField
    private String country;

    public Weather(){
    }

    public Weather(Data maxTemperature, Data minTemperature, Data rainfall, String year, String country) {
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.rainfall = rainfall;
        this.year = year;
        this.country = country;
    }

    public Data getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Data maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Data getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Data minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Data getRainfall() {
        return rainfall;
    }

    public void setRainfall(Data rainfall) {
        this.rainfall = rainfall;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
