package com.example.avi_pc.weatherdemo.base;



interface Presenter<V extends MvpView> {
    void attachView(V mvpView);

    void detachView();
}