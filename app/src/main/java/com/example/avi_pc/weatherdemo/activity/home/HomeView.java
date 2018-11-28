package com.example.avi_pc.weatherdemo.activity.home;

import com.example.avi_pc.weatherdemo.base.MvpView;
import com.github.mikephil.charting.data.BarData;

import java.util.List;

public interface HomeView extends MvpView {
    void initYearsSpinner(List<String> years);

    void initBarConfig();

    BarData getBarChartData();

    void notifyBarData();

    void setBarData(BarData data);

    void invalidateAndFitBarChart();

    void initCountrySpinner();

    void setDefaultSelectedButton();
}
