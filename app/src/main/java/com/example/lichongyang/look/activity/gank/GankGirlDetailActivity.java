package com.example.lichongyang.look.activity.gank;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.lichongyang.look.R;
import com.example.lichongyang.look.base.BaseActivity;
import com.example.lichongyang.look.base.Constants;

public class GankGirlDetailActivity extends BaseActivity{
    private Toolbar toolbar;
    private ImageView girlImageView;

    private String url;
    private String id;

    private Bitmap bitmap;
    private MenuItem menuItem;
    private boolean isLiked;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_gank_girl_detail;
    }

    @Override
    protected void initView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        girlImageView = (ImageView)findViewById(R.id.iv_gank_girl_detail);
    }

    @Override
    protected void setupView(){
        setToolbar(toolbar, "");
        Intent intent = getIntent();
        url = intent.getExtras().getString(Constants.GANK_GIRL_URL);
        id = intent.getExtras().getString(Constants.GANK_GIRL_ID);
        Glide.with(mContext).load(url)
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                girlImageView.setImageBitmap(resource);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.girl_menu, menu);
        menuItem = menu.findItem(R.id.action_like);
        setLikeState(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_like:
                break;
            case R.id.action_save:
                break;
            case R.id.action_share:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setLikeState(boolean likeState) {
        if (likeState){
            menuItem.setIcon(R.mipmap.ic_toolbar_like_p);
            isLiked = true;
        }else{
            menuItem.setIcon(R.mipmap.ic_toolbar_like_n);
            isLiked = false;
        }
    }
}
