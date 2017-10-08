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
import com.example.lichongyang.look.adapter.zhihu.ZhihuSectionAdapter;
import com.example.lichongyang.look.adapter.zhihu.ZhihuThemeAdapter;
import com.example.lichongyang.look.base.BaseFragment;
import com.example.lichongyang.look.contract.zhihu.ZhihuSectionContract;
import com.example.lichongyang.look.contract.zhihu.ZhihuThemeContract;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuSectionItem;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuThemeItem;
import com.example.lichongyang.look.presenter.zhihu.ZhihuSectionPresenter;
import com.example.lichongyang.look.presenter.zhihu.ZhihuThemePresenter;

import java.util.ArrayList;

/**
 * Created by lichongyang on 2017/10/1.
 */

public class ZhihuSectionFragment extends BaseFragment implements ZhihuSectionContract.View{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private ZhihuSectionContract.Presenter mPresenter;
    private ZhihuSectionAdapter mAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new ZhihuSectionPresenter(this);
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
        mAdapter = new ZhihuSectionAdapter(mContext);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mPresenter.getSectionList();
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
    public void setPresenter(ZhihuSectionContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showSectionList(ArrayList<ZhihuSectionItem> items) {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.showSectionList(items);
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
                        mPresenter.getSectionList();
                    }
                }).show();
    }
}
