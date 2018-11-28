package com.example.avi_pc.weatherdemo.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.avi_pc.weatherdemo.R;
import com.example.avi_pc.weatherdemo.WeatherDemoApplication;
import com.example.avi_pc.weatherdemo.injection.component.ActivityComponent;
import com.example.avi_pc.weatherdemo.injection.component.DaggerActivityComponent;
import com.example.avi_pc.weatherdemo.injection.module.ActivityModule;
import com.example.avi_pc.weatherdemo.util.DialogUtil;

public class BaseActivity extends AppCompatActivity implements MvpView {
    private ActivityComponent mActivityComponent;
    protected Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(WeatherDemoApplication.get(this).getApplicationComponent())
                    .build();
        }
        return mActivityComponent;
    }

    @Override
    public void showProgressDialog() {
        dialog = new Dialog(this, R.style.AppTheme_FullScreenProgressBar);
        DialogUtil.showPleaseWaitDialog(dialog);
    }

    @Override
    public void hideProgressDialog() {
      DialogUtil.hideDialog(dialog);
    }

    @Override
    public void showErrorToast(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

}