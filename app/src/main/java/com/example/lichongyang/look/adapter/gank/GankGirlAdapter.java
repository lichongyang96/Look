package com.example.lichongyang.look.adapter.gank;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.lichongyang.look.MyApplication;
import com.example.lichongyang.look.R;
import com.example.lichongyang.look.contract.gank.MeiziContract;
import com.example.lichongyang.look.model.bean.gank.MeiziBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichongyang on 2017/9/27.
 */

public class GankGirlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<MeiziBean> meiziList;
    private LayoutInflater inflater;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public GankGirlAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        meiziList = new ArrayList<>();
    }


    /**
     * 重写getItemViewType并设置StaggeredGridLayoutManager.GAP_HANDLING_NONE解决瀑布流图片重新排列问题
     */
    @Override
    public int getItemViewType(int position) {
        return Math.round((float) MyApplication.SCREEN_WIDTH / (float) meiziList.get(position).getHeight() * 10f);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GirlViewHolder(inflater.inflate(R.layout.item_gank_girl, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final GirlViewHolder holder1 = (GirlViewHolder) holder;
        // 存在记录的高度是先布局再加载图片
        // getAdapterPosition: Returns the Adapter position of the item represented by this ViewHolder.
        if (meiziList.get(holder1.getAdapterPosition()).getHeight() > 0) {
            ViewGroup.LayoutParams layoutParams = holder1.girlIamgeView.getLayoutParams();
            layoutParams.height = meiziList.get(holder1.getAdapterPosition()).getHeight();
        }
        Glide.with(mContext).load(meiziList.get(position).getUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>(MyApplication.SCREEN_WIDTH / 2, MyApplication.SCREEN_HEIGHT / 2) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (holder1.getAdapterPosition() != RecyclerView.NO_POSITION) {
                            if (meiziList.get(holder1.getAdapterPosition()).getHeight() <= 0) {
                                int width = resource.getWidth();
                                int height = resource.getHeight();
                                int realHeight = (MyApplication.SCREEN_WIDTH / 2) * height / width;
                                meiziList.get(holder1.getAdapterPosition()).setHeight(realHeight);
                                ViewGroup.LayoutParams lp = holder1.girlIamgeView.getLayoutParams();
                                lp.height = realHeight;
                            }
                            holder1.girlIamgeView.setImageBitmap(resource);
                        }
                    }
                });
        holder1.girlIamgeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null){
                    View shareView = view.findViewById(R.id.iv_gank_girl);
                    onItemClickListener.onItemClickListener(shareView, meiziList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return meiziList.size();
    }


    public void showLeast(List<MeiziBean> meizi) {
        if (meiziList.size() > 0) {
            meiziList.clear();
        }
        meiziList.addAll(meizi);
        notifyDataSetChanged();
    }

    public void showMore(List<MeiziBean> meizi) {
        meiziList.addAll(meizi);
        for (int i = meiziList.size() - 20; i < meiziList.size(); i++) {
            notifyItemInserted(i);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    private static class GirlViewHolder extends RecyclerView.ViewHolder {
        ImageView girlIamgeView;

        public GirlViewHolder(View itemView) {
            super(itemView);
            girlIamgeView = (ImageView) itemView.findViewById(R.id.iv_gank_girl);
        }
    }

    public interface OnItemClickListener{
        void onItemClickListener(View view, MeiziBean data);
    }
}


