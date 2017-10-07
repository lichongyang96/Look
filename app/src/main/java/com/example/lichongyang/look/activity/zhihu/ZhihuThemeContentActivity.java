package com.example.lichongyang.look.activity.zhihu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lichongyang.look.R;
import com.example.lichongyang.look.adapter.zhihu.ZhihuThemeContentAdapter;
import com.example.lichongyang.look.base.BaseActivity;
import com.example.lichongyang.look.base.Constants;
import com.example.lichongyang.look.contract.zhihu.ZhihuThemeContentContract;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuThemeContent;
import com.example.lichongyang.look.presenter.zhihu.ZhihuThemeContentPresenter;
import com.example.lichongyang.look.utils.PiexlUtils;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by lichongyang on 2017/10/7.
 */

public class ZhihuThemeContentActivity extends BaseActivity implements ZhihuThemeContentContract.View{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AppBarLayout mAppBarLayout;
    private ImageView blurImageView;
    private ImageView originImageView;
    private TextView desTextView;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    private ZhihuThemeContentContract.Presenter mPresenter;
    private ZhihuThemeContentAdapter mAdapter;

    private int id = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ZhihuThemeContentPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhihu_theme;
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_zhihu_theme);
        mAppBarLayout = (AppBarLayout)findViewById(R.id.appbar_zhihu_theme);
        blurImageView = (ImageView)findViewById(R.id.iv_zhihu_theme_blur);
        originImageView = (ImageView)findViewById(R.id.iv_zhihu_theme_origin);
        desTextView = (TextView)findViewById(R.id.tv_zhihu_theme_des);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycle_view_zhihu_theme);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_zhihu_theme);
    }

    @Override
    protected void setupView() {
        Intent intent = getIntent();
        id = Integer.valueOf(intent.getExtras().getString(Constants.ZHIHU_THEME_CONTENT_ID));
        mAdapter = new ZhihuThemeContentAdapter(mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0){
                    mSwipeRefreshLayout.setEnabled(true);
                }else{
                    mSwipeRefreshLayout.setEnabled(false);
                    float rate = (float)(PiexlUtils.dp2px(mContext, 256) + verticalOffset * 2) / PiexlUtils.dp2px(mContext, 256);
                    if (rate >= 0){
                        originImageView.setAlpha(rate);
                    }
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mPresenter.getThemeContent(id);
            }
        });

    }

    @Override
    public void setPresenter(ZhihuThemeContentContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showThemeContent(ZhihuThemeContent zhihuThemeContent) {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        setToolbar(mToolbar, zhihuThemeContent.getName());
        Glide.with(mContext).load(zhihuThemeContent.getBackground())
                .crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(originImageView);
        Glide.with(mContext).load(zhihuThemeContent.getBackground()).bitmapTransform(new BlurTransformation(mContext, null)).into(blurImageView);
        desTextView.setText(zhihuThemeContent.getDescription());
        mAdapter.showLeastContent(zhihuThemeContent.getStories());
    }

    @Override
    public void showError(String error) {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        Snackbar.make(mRecyclerView, getString(R.string.error_message), Snackbar.LENGTH_SHORT)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.getThemeContent(id);
                    }
                }).show();
    }

    @Override
    public int getId() {
        return id;
    }
}
