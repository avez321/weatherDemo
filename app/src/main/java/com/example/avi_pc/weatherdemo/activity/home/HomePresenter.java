package com.example.avi_pc.weatherdemo.activity.home;


import android.graphics.Color;
import android.util.Log;

import com.example.avi_pc.weatherdemo.Constants;
import com.example.avi_pc.weatherdemo.base.BasePresenter;
import com.example.avi_pc.weatherdemo.database.TemperatureTable;
import com.example.avi_pc.weatherdemo.model.Data;
import com.example.avi_pc.weatherdemo.model.Weather;
import com.example.avi_pc.weatherdemo.remote.WeatherDemoAppApi;
import com.example.avi_pc.weatherdemo.remote.observer.CallbackObserverWrapper;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends BasePresenter<HomeView> {

    private WeatherDemoAppApi weatherDemoAppApi;
    private String country = "UK";
    private TemperatureTable temperatureTable;
    private boolean isTemperature = true;


    @Inject
    HomePresenter(WeatherDemoAppApi weatherDemoAppApi, TemperatureTable temperatureTable) {
        this.weatherDemoAppApi = weatherDemoAppApi;
        this.temperatureTable = temperatureTable;

    }

    public void initYearSpinner() {
        List<String> years = temperatureTable.getYears(country);
        getMvpView().initYearsSpinner(years);
    }

    public void getWeatherDataFromServer() {
        getMvpView().showProgressDialog();
        Observable.zip(getMaxTemperatureObservable(),
                getMinTemperatureObservable(), getRainFallObservable(), new Function3<Data, Data, Data, Weather>() {
                    @Override
                    public Weather apply(Data maxTemperature, Data minTemperature, Data rainfall) throws Exception {
                        return new Weather(maxTemperature, minTemperature, rainfall, maxTemperature.getYear(), country);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .toList().map(new Function<List<Weather>, List<Weather>>() {
            @Override
            public List<Weather> apply(List<Weather> weathers) throws Exception {
                temperatureTable.createOrUpdateUser(weathers);
                return weathers;
            }
        }).subscribe(new SingleObserver<List<Weather>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<Weather> weathers) {
                initYearSpinner();
                getMvpView().hideProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().hideProgressDialog();
                getMvpView().showErrorToast(e.toString());
            }
        });
    }


    public void checkIsDataExists() {
        getMvpView().showProgressDialog();
        getWeatherDataObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CallbackObserverWrapper<Boolean>() {
            @Override
            protected void onSuccess(Boolean result) {
                getMvpView().hideProgressDialog();
                if (result) {
                    initYearSpinner();
                } else {
                    getWeatherDataFromServer();
                }
            }

            @Override
            protected void onFailure(String error) {
                getMvpView().hideProgressDialog();
                getMvpView().showErrorToast(error);
            }
        });
    }


    private io.reactivex.Observable<Data> getMinTemperatureObservable() {
        return weatherDemoAppApi.getWhetherData(Constants.TMIN, country).flatMap(new Function<List<Data>, ObservableSource<Data>>() {
            @Override
            public ObservableSource<Data> apply(List<Data> data) throws Exception {
                return io.reactivex.Observable.fromIterable(data);
            }
        });
    }


    private io.reactivex.Observable<Data> getMaxTemperatureObservable() {
        return weatherDemoAppApi.getWhetherData(Constants.TMAX, country).flatMap(new Function<List<Data>, ObservableSource<Data>>() {
            @Override
            public ObservableSource<Data> apply(List<Data> data) throws Exception {
                return io.reactivex.Observable.fromIterable(data);
            }
        });
    }


    private io.reactivex.Observable<Data> getRainFallObservable() {
        return weatherDemoAppApi.getWhetherData(Constants.RAINFALL, country).flatMap(new Function<List<Data>, ObservableSource<Data>>() {
            @Override
            public ObservableSource<Data> apply(List<Data> data) throws Exception {
                return io.reactivex.Observable.fromIterable(data);
            }
        });
    }


    private io.reactivex.Observable<Boolean> getWeatherDataObservable() {
        return io.reactivex.Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                List<Weather> weathers = temperatureTable.getWeatherData(country);
                return weathers.size() == 1;
            }
        });
    }


    private io.reactivex.Observable<List<Weather>> getTemperatureObservable(final String year) {
        return io.reactivex.Observable.fromCallable(new Callable<List<Weather>>() {
            @Override
            public List<Weather> call() throws Exception {
                List<Weather> weathers = temperatureTable.getTemperatureList(year, country);
                return weathers;
            }
        });
    }


    public void getYearlyWeatherData(String year) {
        getTemperatureObservable(year).subscribe(new CallbackObserverWrapper<List<Weather>>() {
            @Override
            protected void onSuccess(List<Weather> weather) {
                initBarChart(weather);
            }

            @Override
            protected void onFailure(String error) {

            }
        });
    }


    public void getCountryWeatherData(String country) {
        this.country = country;
        checkIsDataExists();
    }


    public void initBarChart(List<Weather> weather) {
        getMvpView().initBarConfig();

        BarDataSet set = null;
        if (isTemperature) {
            ArrayList<BarEntry> values = new ArrayList<>();
            for (int i = 0; i < weather.size(); i++) {
                float maxTemp = weather.get(i).getMaxTemperature().getValue();
                float minTemp = weather.get(i).getMinTemperature().getValue();


                values.add(new BarEntry(i, new float[]{maxTemp, minTemp}));
            }


            set = new BarDataSet(values, "");
            set.setDrawIcons(false);
            set.setValueTextSize(7f);
            set.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set.setColors(getColors());
            set.setStackLabels(Constants.TEMPERATURE_LABELS);
        } else {
            ArrayList<BarEntry> values = new ArrayList<>();
            for (int i = 0; i < weather.size(); i++) {
                float rainfall = weather.get(i).getRainfall().getValue();
                values.add(new BarEntry(i, new float[]{rainfall}));
            }

            set = new BarDataSet(values, Constants.RAINFALL);
            set.setDrawIcons(false);
            set.setValueTextSize(7f);
            set.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set.setColors(Color.BLUE);

        }


        BarData data = new BarData(set);
        getMvpView().setBarData(data);


    }

    private int[] getColors() {
        return new int[]{Color.GREEN, Color.RED};
    }

    public String getXAxisData(float value) {
        if (value == 0.0) {
            return "Jan";

        } else if (value == 1.0) {
            return "Feb";

        } else if (value == 2.0) {
            return "Mar";

        } else if (value == 3.0) {
            return "Apr";

        } else if (value == 4.0) {
            return "May";

        } else if (value == 5.0) {
            return "Jun";

        } else if (value == 6.0) {
            return "Jul";

        } else if (value == 7.0) {
            return "Aug";

        } else if (value == 8.0) {
            return "Sep";

        } else if (value == 9.0) {
            return "Oct";
        } else if (value == 10.0) {
            return "Nov";

        } else if (value == 11.0) {
            return "Dec";

        }
        return "";


    }

    public void setTemperature(boolean temperature) {
        isTemperature = temperature;
    }

    public void init() {
        getMvpView().initCountrySpinner();
        getMvpView().setDefaultSelectedButton();
    }
}
