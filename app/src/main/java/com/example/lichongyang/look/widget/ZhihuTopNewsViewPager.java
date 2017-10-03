package com.example.lichongyang.look.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lichongyang.look.MyApplication;
import com.example.lichongyang.look.contract.gank.MeiziContract;
import com.example.lichongyang.look.contract.gank.TechContract;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDailyTopItem;
import com.example.lichongyang.look.utils.PiexlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by lichongyang on 2017/10/2.
 */

public class ZhihuTopNewsViewPager extends RelativeLayout{
    private Context context;
    private LinearLayout dotLayout;
    private ViewPager viewPager;
    private ViewPagerClickListener listener;
    private List<View> dotList;
    private int currentItem = 0;
    private int oldItem = 0;
    private List<ImageView> images;
    private ScheduledExecutorService scheduledExecutorService;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(currentItem);
        }
    };

    public ZhihuTopNewsViewPager(Context context) {
        super(context);
        this.context = context;
        setView();
    }

    public ZhihuTopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setView();
    }

    private void setView(){
        viewPager = new ViewPager(context);
        LayoutParams viewPagerLayoutParams = new LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
        viewPager.setLayoutParams(viewPagerLayoutParams);

        dotLayout = new LinearLayout(context);
        LayoutParams dotLayoutParams = new LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
        dotLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        dotLayoutParams.setMargins(0, 0, 0, PiexlUtils.dp2px(context, 10));
        dotLayout.setLayoutParams(dotLayoutParams);
        dotLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        this.addView(viewPager);
        this.addView(dotLayout);
    }

    public void init(final List<ZhihuDailyTopItem> items, final TextView textView, ViewPagerClickListener viewPagerClickListener){
        this.listener = viewPagerClickListener;
        images = new ArrayList<>();
        dotList = new ArrayList<>();

        for (int i = 0; i < items.size(); i++){
            final ZhihuDailyTopItem item = items.get(i);
            final ImageView imageView = new ImageView(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        listener.onClick(item);
                    }
                }
            });
            Glide.with(MyApplication.getContext()).load(item.getImage()).centerCrop().into(imageView);
            images.add(imageView);
        }
        viewPager.setAdapter(new MyViewPagerAdapter(images));
        textView.setText(items.get(0).getTitle());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textView.setText(items.get(position).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void startAutoRun(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new ViewPagerTask(), 5, 5, TimeUnit.SECONDS);
    }

    public void stopAutoRun(){
        if (scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
        }
    }

    public class ViewPagerTask implements Runnable{

        @Override
        public void run() {
            if (images != null){
                currentItem = (currentItem + 1) % images.size();
                handler.obtainMessage().sendToTarget();
            }
        }
    }


    public class MyViewPagerAdapter extends PagerAdapter{
        private List<? extends View> views;

        public MyViewPagerAdapter(List<? extends View> views){
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (views.size() > 0 && views.get(position % views.size()).getParent() != null){
                ((ViewPager)views.get(position % views.size()).getParent()).removeView(views.get(position % views.size()));
            }
            try{
                ((ViewPager)container).addView(views.get(position % views.size()), 0);
            }catch (Exception e){
                e.printStackTrace();
            }
            return views.get(position % views.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView(views.get(position % views.size()));
        }
    }

    /**
     * viewpager点击事件接口
     */
    public interface ViewPagerClickListener{
        void onClick(ZhihuDailyTopItem item);
    }
}
