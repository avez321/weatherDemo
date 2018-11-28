package com.example.avi_pc.weatherdemo.activity.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.avi_pc.weatherdemo.Constants;
import com.example.avi_pc.weatherdemo.R;
import com.example.avi_pc.weatherdemo.base.BaseActivity;
import com.example.avi_pc.weatherdemo.databinding.ActivityHomeBinding;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

import javax.inject.Inject;


public class HomeActivity extends BaseActivity implements HomeView {
    private ActivityHomeBinding activityHomeBinding;

    @Inject
    HomePresenter homePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        getActivityComponent().inject(this);
        homePresenter.attachView(this);
        homePresenter.init();

        activityHomeBinding.btnRainfall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePresenter.setTemperature(false);
                v.setSelected(true);
                activityHomeBinding.btnTemperature.setSelected(false);
                homePresenter.getYearlyWeatherData(activityHomeBinding.yearsSpinner.getSelectedItem().toString());
            }
        });

        activityHomeBinding.btnTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePresenter.setTemperature(true);
                v.setSelected(true);
                activityHomeBinding.btnRainfall.setSelected(false);
                homePresenter.getYearlyWeatherData(activityHomeBinding.yearsSpinner.getSelectedItem().toString());
            }
        });
    }

    @Override
    public void initYearsSpinner(List<String> years) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityHomeBinding.yearsSpinner.setAdapter(adapter);


        activityHomeBinding.yearsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                homePresenter.getYearlyWeatherData(activityHomeBinding.yearsSpinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void initBarConfig() {
        activityHomeBinding.barChart.setDrawGridBackground(false);
        activityHomeBinding.barChart.getDescription().setEnabled(false);
        activityHomeBinding.barChart.setPinchZoom(false);

        activityHomeBinding.barChart.setDrawBarShadow(false);
        activityHomeBinding.barChart.setDrawValueAboveBar(true);
        activityHomeBinding.barChart.setHighlightFullBarEnabled(false);

        activityHomeBinding.barChart.getAxisLeft().setEnabled(false);
        activityHomeBinding.barChart.getAxisRight().setDrawGridLines(false);
        activityHomeBinding.barChart.getAxisRight().setDrawZeroLine(true);
        activityHomeBinding.barChart.getAxisRight().setTextSize(Constants.TEXT_SIZE);


        XAxis xAxis = activityHomeBinding.barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(Constants.TEXT_SIZE);
        xAxis.setAxisMinimum(Constants.MINIMUM_AXIS);
        xAxis.setAxisMaximum(Constants.MAXIMUM_AXIS);
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelCount(Constants.LABEL_COUNT);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return homePresenter.getXAxisData(value);
            }
        });

        Legend l = activityHomeBinding.barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(Constants.FORM_SIZE);
        l.setFormToTextSpace(Constants.TEXT_SPACE);
        l.setXEntrySpace(Constants.ENTRY_SPACE);

    }


    @Override
    public void initCountrySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityHomeBinding.countrySpinner.setAdapter(adapter);
        activityHomeBinding.countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                homePresenter.getCountryWeatherData(activityHomeBinding.countrySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void setDefaultSelectedButton() {
        activityHomeBinding.btnTemperature.setSelected(true);
    }

    @Override
    public BarData getBarChartData() {
        return activityHomeBinding.barChart.getBarData();
    }

    @Override
    public void notifyBarData() {
        activityHomeBinding.barChart.getData().notifyDataChanged();
        activityHomeBinding.barChart.notifyDataSetChanged();
    }

    @Override
    public void setBarData(BarData data) {
        activityHomeBinding.barChart.setData(data);
        activityHomeBinding.barChart.invalidate();
    }

    @Override
    public void invalidateAndFitBarChart() {
        activityHomeBinding.barChart.setFitBars(true);
        activityHomeBinding.barChart.invalidate();
    }
}
