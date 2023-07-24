package com.example.kyapplication.http;

import okhttp3.OkHttpClient;

abstract class BaseRetrofitClient {

    private final int TIME_OUT = 7;

    private OkHttpClient client;

    private final OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder.build();
    }

}
