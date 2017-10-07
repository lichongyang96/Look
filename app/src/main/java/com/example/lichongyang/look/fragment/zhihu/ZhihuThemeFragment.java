package com.example.lichongyang.look.fragment.zhihu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lichongyang.look.R;
import com.example.lichongyang.look.adapter.zhihu.ZhihuThemeAdapter;
import com.example.lichongyang.look.base.BaseFragment;
import com.example.lichongyang.look.contract.zhihu.ZhihuThemeContract;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuThemeItem;
import com.example.lichongyang.look.presenter.zhihu.ZhihuThemePresenter;

import java.util.ArrayList;

/**
 * Created by lichongyang on 2017/10/1.
 */

public class ZhihuThemeFragment extends BaseFragment implements ZhihuThemeContract.View{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private ZhihuThemeContract.Presenter mPresenter;
    private ZhihuThemeAdapter mAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new ZhihuThemePresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @Override
    protected void setupView() {
        mAdapter = new ZhihuThemeAdapter(mContext);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mPresenter.getThemeList();
            }
        });
    }

    @Override
    protected void initView(android.view.View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycle_view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_common_list;
    }

    @Override
    public void setPresenter(ZhihuThemeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showThemeList(ArrayList<ZhihuThemeItem> items) {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.showThemeList(items);
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
                        mPresenter.getThemeList();
                    }
                }).show();
    }
}
