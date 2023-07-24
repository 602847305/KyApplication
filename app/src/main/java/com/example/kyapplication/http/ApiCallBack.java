package com.example.kyapplication.http;

import com.example.kyapplication.bean.BaseResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallBack<T extends BaseResponseBody> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        T data = response.body();
        onResponse(call,data);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
//        Timber.w(t);
        onError(call,t);
    }


    public abstract void onResponse(Call<T> call,T result);

    public abstract void onError(Call<T> call,Throwable t);

}
