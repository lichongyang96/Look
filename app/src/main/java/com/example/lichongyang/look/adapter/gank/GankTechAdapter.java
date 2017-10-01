package com.example.lichongyang.look.adapter.gank;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lichongyang.look.R;
import com.example.lichongyang.look.fragment.gank.GankMainFragment;
import com.example.lichongyang.look.model.bean.gank.TechBean;
import com.example.lichongyang.look.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichongyang on 2017/9/29.
 */

public class GankTechAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<TechBean> techList;
    private OnItemClickListener mOnItemClickListener;
    private LayoutInflater inflater;

    private String mTechType;

    public GankTechAdapter(Context context, String techType){
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        techList = new ArrayList<>();
        this.mTechType = techType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TechViewHolder(inflater.inflate(R.layout.item_gank_tech, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TechViewHolder holder1 = (TechViewHolder)holder;
        if (mTechType == GankMainFragment.tabTitle[0]){
            holder1.iconImageView.setImageResource(R.mipmap.ic_android);
        }else if (mTechType == GankMainFragment.tabTitle[1]){
            holder1.iconImageView.setImageResource(R.mipmap.ic_ios);
        }else if (mTechType == GankMainFragment.tabTitle[2]){
            holder1.iconImageView.setImageResource(R.mipmap.ic_web);
        }
        holder1.titleTextView.setText(techList.get(position).getDesc());
        holder1.authorTextView.setText(techList.get(position).getWho());
        holder1.timeTextView.setText(TimeUtils.formatDateTime(TimeUtils.subStandardTime(techList.get(position).getPublishedAt()), true));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    CardView cardView = (CardView)view.findViewById(R.id.cv_tech_content);
                    mOnItemClickListener.onItemClick(cardView, techList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return techList.size();
    }

    public void showLeast(List<TechBean> tech) {
        if (techList.size() > 0) {
            techList.clear();
        }
        techList.addAll(tech);
        notifyDataSetChanged();
    }

    public void showMore(List<TechBean> tech) {
        techList.addAll(tech);
        notifyDataSetChanged();
    }

    private static class TechViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImageView;
        TextView titleTextView;
        TextView authorTextView;
        TextView timeTextView;

        public TechViewHolder(View itemView) {
            super(itemView);
            iconImageView = (ImageView)itemView.findViewById(R.id.iv_tech_icon);
            titleTextView = (TextView)itemView.findViewById(R.id.tv_tech_title);
            authorTextView = (TextView)itemView.findViewById(R.id.tv_tech_author);
            timeTextView = (TextView)itemView.findViewById(R.id.tv_tech_time);
        }
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, TechBean data);
    }
}
