package com.example.lichongyang.look.adapter.zhihu;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lichongyang.look.R;
import com.example.lichongyang.look.contract.gank.MeiziContract;
import com.example.lichongyang.look.model.bean.gank.MeiziBean;
import com.example.lichongyang.look.model.bean.gank.TechBean;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDaily;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDailyItem;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDailyTopItem;
import com.example.lichongyang.look.utils.PiexlUtils;
import com.example.lichongyang.look.widget.ZhihuTopNewsViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichongyang on 2017/10/3.
 */

public class ZhihuDailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private ZhihuDaily mZhihuDaily;
    private LayoutInflater inflater;

    private int status = 1;
    public static final int LOAD_MORE = 0;
    public static final int LOAD_PULL_TO = 1;
    public static final int LOAD_NONE = 2;
    public static final int LOAD_END = 3;

    public static final int TYPE_TOP = -1;
    public static final int TYPE_FOOTER = -2;

    public ZhihuDailyAdapter(Context context, ZhihuDaily zhihuDaily){
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.mZhihuDaily = zhihuDaily;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TOP) {
            return new TopItemViewHolder(inflater.inflate(R.layout.item_zhihu_daily_top, parent, false));
        }else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(inflater.inflate(R.layout.view_loadmore, parent, false));
        }else{
            return new ItemViewHolder(inflater.inflate(R.layout.item_zhihu_daily, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopItemViewHolder){
            ((TopItemViewHolder)holder).bindItem(mZhihuDaily.getZhihuDailyTopItems());
        }else if (holder instanceof FooterViewHolder){
            ((FooterViewHolder)holder).bindItem();
        }else{
            ((ItemViewHolder)holder).bindItem(mZhihuDaily.getZhihuDailyItems().get(position - 1));
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof TopItemViewHolder){
            TopItemViewHolder hodler1 = (TopItemViewHolder)holder;
            hodler1.viewPager.startAutoRun();
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof TopItemViewHolder){
            TopItemViewHolder hodler1 = (TopItemViewHolder)holder;
            hodler1.viewPager.stopAutoRun();
        }
    }

    @Override
    public int getItemCount() {
        return mZhihuDaily.getZhihuDailyItems().size() + 2;
    }

    public void updateStatus(int status){
        this.status = status;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (mZhihuDaily.getZhihuDailyTopItems() != null){
            if (position == 0){
                return TYPE_TOP;
            }else if (position + 1 == getItemCount()){
                return TYPE_FOOTER;
            }else{
                return position;
            }
        }else if (position + 1 == getItemCount()){
            return TYPE_FOOTER;
        }else{
            return position;
        }
    }

    class TopItemViewHolder extends RecyclerView.ViewHolder{
        ZhihuTopNewsViewPager viewPager;
        TextView textView;

        public TopItemViewHolder(View itemView) {
            super(itemView);
            viewPager = (ZhihuTopNewsViewPager)itemView.findViewById(R.id.vp_zhihu_daily_top);
            textView = (TextView)itemView.findViewById(R.id.tv_zhihu_daily_top_title);
        }

        public void bindItem(List<ZhihuDailyTopItem> zhihuDailyTopItems){
            viewPager.init(zhihuDailyTopItems, textView, new ZhihuTopNewsViewPager.ViewPagerClickListener(){
                @Override
                public void onClick(ZhihuDailyTopItem item) {
                    // TODO: 2017/10/3 启动文章详情页面 
                    mContext.startActivity(null);
                }
            });
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.tv_loadmore);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progress);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PiexlUtils.dp2px(mContext, 40));
            itemView.setLayoutParams(lp);
        }

        public void bindItem(){
            switch (status){
                case LOAD_MORE:
                    progressBar.setVisibility(View.VISIBLE);
                    textView.setText("正在加载...");
                    itemView.setVisibility(View.VISIBLE);
                    break;
                case LOAD_PULL_TO:
                    progressBar.setVisibility(View.GONE);
                    textView.setText("上拉加载更多...");
                    itemView.setVisibility(View.VISIBLE);
                    break;
                case LOAD_NONE:
                    progressBar.setVisibility(View.GONE);
                    textView.setText("已无法加载更多");
                    break;
                case LOAD_END:
                    itemView.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textView;
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cv_zhihu_daily_item);
            textView = (TextView) itemView.findViewById(R.id.tv_zhihu_daily_item_title);
            imageView = (ImageView)itemView.findViewById(R.id.iv_zhihu_daily_item);
        }

        public void bindItem(final ZhihuDailyItem item){
            textView.setText(item.getTitle());
            String imageUrl = item.getImage();
            Glide.with(mContext).load(imageUrl).centerCrop().into(imageView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2017/10/3 启动文章详情页面
                    mContext.startActivity(null);
                }
            });
        }
    }
}
