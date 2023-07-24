package com.example.kyapplication.http;

import androidx.lifecycle.LiveData;

import com.example.kyapplication.bean.BaseResponseBody;
import com.example.kyapplication.bean.FeedArticleListData;

public class DataRepository extends BaseRepository {

    private static volatile DataRepository mDataRepository;
    private static final Object lock = new Object();

    public static DataRepository getInstance(HttpService apiService) {
        if (mDataRepository == null) {
            synchronized (lock) {
                if (mDataRepository == null)
                    mDataRepository = new DataRepository(apiService);
            }
        }
        return mDataRepository;
    }

    public DataRepository(HttpService apiService) {
        super(apiService);
    }

//    public LiveData<BaseResponseBody<FeedArticleListData>>



}
