package com.example.lichongyang.look.adapter.zhihu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lichongyang.look.R;
import com.example.lichongyang.look.activity.zhihu.ZhihuDailyDetailActivity;
import com.example.lichongyang.look.base.Constants;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuSectionStory;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuThemeStory;

import java.util.ArrayList;

/**
 * todo: glide加载图片错位问题。
 * Created by lichongyang on 2017/10/7.
 */

public class ZhihuSectionContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<ZhihuSectionStory> stories;

    public ZhihuSectionContentAdapter(Context context){
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        stories = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.item_zhihu_daily, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder)holder).bindItem(stories.get(position));
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void showLeastContent(ArrayList<ZhihuSectionStory> newStories){
        if (stories != null && stories.size() > 0){
            stories.clear();
        }
        stories.addAll(newStories);
        notifyDataSetChanged();
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        CardView cardView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.tv_zhihu_daily_item_title);
            imageView = (ImageView)itemView.findViewById(R.id.iv_zhihu_daily_item);
            cardView = (CardView)itemView.findViewById(R.id.cv_zhihu_daily_item);
        }

        public void bindItem(final ZhihuSectionStory story){
            textView.setText(story.getTitle());
            if (story.getImages() != null && story.getImages().size() > 0) {
                Glide.with(mContext).load(story.getImages().get(0)).centerCrop().into(imageView);
            }
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.setClass(mContext, ZhihuDailyDetailActivity.class);
                            intent.putExtra(Constants.ZHIHU_DAILY_DETAIL_ID, String.valueOf(story.getId()));
                            mContext.startActivity(intent);
                        }
                    });
                }
            });
        }
    }
}
