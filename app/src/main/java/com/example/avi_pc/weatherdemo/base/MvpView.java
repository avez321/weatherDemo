package com.example.avi_pc.weatherdemo.base;



public interface MvpView {
    void showProgressDialog();

    void hideProgressDialog();

    void showErrorToast(String error);
}