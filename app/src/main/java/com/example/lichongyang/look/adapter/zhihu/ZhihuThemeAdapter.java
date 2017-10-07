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
import com.example.lichongyang.look.activity.zhihu.ZhihuThemeContentActivity;
import com.example.lichongyang.look.base.Constants;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuThemeContent;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuThemeItem;

import java.util.ArrayList;

/**
 * Created by lichongyang on 2017/10/7.
 */

public class ZhihuThemeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<ZhihuThemeItem> items;
    private OnItemClickListener mOnItemClickListener;

    public ZhihuThemeAdapter(Context context){
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        items = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.item_zhihu_theme, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder)holder).bindItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void showThemeList(ArrayList<ZhihuThemeItem> newItems){
        if(items != null && items.size() > 0){
            items.clear();
        }
        items = newItems;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cv_zhihu_theme_item);
            imageView = (ImageView)itemView.findViewById(R.id.iv_zhihu_theme_bg);
            textView = (TextView)itemView.findViewById(R.id.tv_zhihu_theme_title);
        }

        public void bindItem(final ZhihuThemeItem zhihuThemeItem){
            String id = zhihuThemeItem.getId();
            Glide.with(mContext).load(zhihuThemeItem.getThumbnail()).centerCrop().into(imageView);
            textView.setText(zhihuThemeItem.getName());
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, ZhihuThemeContentActivity.class);
                    intent.putExtra(Constants.ZHIHU_THEME_CONTENT_ID, String.valueOf(zhihuThemeItem.getId()));
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, ZhihuThemeItem item);
    }
}
