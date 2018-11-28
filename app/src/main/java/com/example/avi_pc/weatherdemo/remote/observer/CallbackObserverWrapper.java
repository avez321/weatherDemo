package com.example.avi_pc.weatherdemo.remote.observer;




import com.example.avi_pc.weatherdemo.Constants;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public abstract class CallbackObserverWrapper<R> extends DisposableObserver<R> {

    protected abstract void onSuccess(R result);

    protected abstract void onFailure(String error);



    @Override
    public void onNext(R result) {
        onSuccess(result);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            onFailure(getErrorMessage(responseBody));
        } else if (e instanceof SocketTimeoutException) {
            onFailure("Network Timeout");
        } else if (e instanceof IOException) {
            onFailure("Failed to connect to server");
        } else onFailure(e.getLocalizedMessage());
    }

    @Override
    public void onComplete() {
    }

    private String getErrorMessage(ResponseBody responseBody) {
        String responseString = null;
        try {
            responseString= responseBody.string();
            JSONObject jsonObject = new JSONObject(responseString);
            return jsonObject.getString(Constants.API_ERROR_RESPONSE_KEY);
        } catch (Exception e) {
            return responseString;
        }
    }
}