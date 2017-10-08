package com.example.lichongyang.look.activity.zhihu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lichongyang.look.R;
import com.example.lichongyang.look.adapter.zhihu.ZhihuSectionContentAdapter;
import com.example.lichongyang.look.base.BaseActivity;
import com.example.lichongyang.look.base.Constants;
import com.example.lichongyang.look.contract.zhihu.ZhihuSectionContentContract;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuSectionContent;
import com.example.lichongyang.look.presenter.zhihu.ZhihuSectionContentPresenter;

/**
 * Created by lichongyang on 2017/10/8.
 */

public class ZhihuSectionContentActivity extends BaseActivity implements ZhihuSectionContentContract.View{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    private ZhihuSectionContentContract.Presenter mPresenter;
    private ZhihuSectionContentAdapter mAdapter;

    private int id = -1;
    private String title = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ZhihuSectionContentPresenter(this);
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
        return R.layout.activity_zhihu_section;
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_zhihu_section);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycle_view_zhihu_section);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_zhihu_section);
    }

    @Override
    protected void setupView() {
        Intent intent = getIntent();
        id = Integer.valueOf(intent.getExtras().getString(Constants.ZHIHU_SECTION_CONTENT_ID));
        title = intent.getExtras().getString(Constants.ZHIHU_SECTION_CONTENT_TITLE);
        setToolbar(mToolbar, title);
        mAdapter = new ZhihuSectionContentAdapter(mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mPresenter.getSectionContent(id);
            }
        });
    }

    @Override
    public void setPresenter(ZhihuSectionContentContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showSectionContent(ZhihuSectionContent zhihuSectionContent) {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.showLeastContent(zhihuSectionContent.getStories());
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
                        mPresenter.getSectionContent(id);
                    }
                }).show();
    }

    @Override
    public int getId() {
        return id;
    }
}
