package com.example.kyapplication.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.kyapplication.R;
import com.example.kyapplication.bean.BaseResponseBody;
import com.example.kyapplication.bean.FeedArticleListData;
import com.example.kyapplication.http.HttpService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentB extends Fragment {
    private View rootView;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout refreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_b,container,false);
        mRecyclerView = rootView.findViewById(R.id.recycler_view_b);
        refreshLayout = rootView.findViewById(R.id.refresh_layout_b);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initData();







    }

    private void initData()
    {
        //获取Retrofit对象
        Retrofit retrofit = new Retrofit.Builder().baseUrl(HttpService.HOST).addConverterFactory(GsonConverterFactory.create()).build();
        //通过Retrofit获取接口服务对象
        HttpService server = retrofit.create(HttpService.class);
        //接口对象调用其方法获取call对象
        Call<BaseResponseBody<FeedArticleListData>> data = server.getCall(0);
        //call执行请求
        data.enqueue(new Callback<BaseResponseBody<FeedArticleListData>>() {
            @Override
            public void onResponse(Call<BaseResponseBody<FeedArticleListData>> call, Response<BaseResponseBody<FeedArticleListData>> response) {
                try {
                    if (response.body()!=null){
                    String json = response.body().getData().getDatas().get(0).getChapterName();
                    Log.e("TAG", "onResponse: " +  json);}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponseBody<FeedArticleListData>> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }
}
