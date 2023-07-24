package com.example.kyapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kyapplication.R;
import com.example.kyapplication.bean.FeedArticleListData;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int BANNER =1,ARTICLE = 2;



    private FeedArticleListData feedArticleListData;

    public ArticleAdapter(FeedArticleListData feedArticleListData)
    {
        this.feedArticleListData = feedArticleListData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == ARTICLE)
            return new ArticleViewHolder(inflater.inflate(R.layout.item_article,parent,false));




            switch (viewType)
            {
                case BANNER:
                    break;
                case ARTICLE:

                    break;
            }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        if(feedArticleListData==null)
            return 0;
        return feedArticleListData.getSize();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder{
        TextView tvType,tvTitle,tvDate;
       public ArticleViewHolder(@NonNull View itemView) {
           super(itemView);
           tvType = itemView.findViewById(R.id.type);
           tvTitle = itemView.findViewById(R.id.title);
           tvDate = itemView.findViewById(R.id.date);
       }
   }

   static class BannerViewHolder extends RecyclerView.ViewHolder
   {

       public BannerViewHolder(@NonNull View itemView) {
           super(itemView);
       }
   }

}
