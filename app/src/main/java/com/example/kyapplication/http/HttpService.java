package com.example.kyapplication.http;

import com.example.kyapplication.bean.BaseResponseBody;
import com.example.kyapplication.bean.FeedArticleListData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HttpService {

    String HOST = "https://www.wanandroid.com/";

    @GET("article/list/{num}/json")
    Call<BaseResponseBody<FeedArticleListData>> getCall(@Path("num") int num);

}
