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
import com.example.lichongyang.look.activity.zhihu.ZhihuSectionContentActivity;
import com.example.lichongyang.look.activity.zhihu.ZhihuThemeContentActivity;
import com.example.lichongyang.look.base.Constants;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuSection;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuSectionItem;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuThemeItem;

import java.util.ArrayList;

/**
 * Created by lichongyang on 2017/10/7.
 */

public class ZhihuSectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<ZhihuSectionItem> items;

    public ZhihuSectionAdapter(Context context){
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        items = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.item_zhihu_section, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder)holder).bindItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void showSectionList(ArrayList<ZhihuSectionItem> newItems){
        if(items != null && items.size() > 0){
            items.clear();
        }
        items = newItems;
        notifyDataSetChanged();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView titleTextView;
        TextView desTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cv_zhihu_section_item);
            imageView = (ImageView)itemView.findViewById(R.id.iv_zhihu_section_bg);
            titleTextView = (TextView)itemView.findViewById(R.id.tv_zhihu_section_title);
            desTextView = (TextView)itemView.findViewById(R.id.tv_zhihu_section_des);
        }

        public void bindItem(final ZhihuSectionItem zhihuSectionItem){
            Glide.with(mContext).load(zhihuSectionItem.getThumbnail()).centerCrop().into(imageView);
            titleTextView.setText(zhihuSectionItem.getName());
            desTextView.setText(zhihuSectionItem.getDescription());
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, ZhihuSectionContentActivity.class);
                    intent.putExtra(Constants.ZHIHU_SECTION_CONTENT_ID, String.valueOf(zhihuSectionItem.getId()));
                    intent.putExtra(Constants.ZHIHU_SECTION_CONTENT_TITLE, zhihuSectionItem.getName());
                    mContext.startActivity(intent);
                }
            });
        }
    }

}
