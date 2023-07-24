package com.example.kyapplication.http;

import com.example.kyapplication.bean.BaseResponseBody;
import com.example.kyapplication.bean.FeedArticleListData;

import retrofit2.Call;

public class HttpHelperImpl implements HttpHelper{

    private HttpService httpApi;

    HttpHelperImpl(HttpService httpApi)
    {
        this.httpApi = httpApi;
    }

    @Override
    public Call<BaseResponseBody<FeedArticleListData>> getFeedArticleList(int pageNum) {
        return httpApi.getCall(pageNum);
    }



}
