package com.example.kyapplication.http;

import com.example.kyapplication.bean.BaseResponseBody;

public class BaseRepository {

    private HttpService apiService;

    public BaseRepository(HttpService apiService){
        this.apiService = apiService;
    }

    public HttpService getApiService() {
        return apiService;
    }

    public boolean isSuccess(BaseResponseBody result){
        return result!=null && result.isSuccess();
    }


}
