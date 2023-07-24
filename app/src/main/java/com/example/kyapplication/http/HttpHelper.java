package com.example.kyapplication.http;


import com.example.kyapplication.bean.BaseResponseBody;
import com.example.kyapplication.bean.FeedArticleListData;

import retrofit2.Call;

public interface HttpHelper {

    /**
     * 获取feed文章列表
     *
     * @param pageNum 页数
     * @return feed文章列表数据
     */
    Call<BaseResponseBody<FeedArticleListData>> getFeedArticleList(int pageNum);


}
