package com.example.lichongyang.look.fragment.zhihu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lichongyang.look.R;
import com.example.lichongyang.look.adapter.zhihu.ZhihuHotAdapter;
import com.example.lichongyang.look.base.BaseFragment;
import com.example.lichongyang.look.contract.zhihu.ZhihuHotContract;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuHotItem;
import com.example.lichongyang.look.presenter.zhihu.ZhihuHotPresenter;

import java.util.ArrayList;

/**
 * Created by lichongyang on 2017/10/1.
 */

public class ZhihuHotFragment extends BaseFragment implements ZhihuHotContract.View{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private ZhihuHotContract.Presenter mPresenter;
    private ZhihuHotAdapter mAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new ZhihuHotPresenter(this);
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
        mAdapter = new ZhihuHotAdapter(mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mPresenter.getHotList();
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
    public void setPresenter(ZhihuHotContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showHotList(ArrayList<ZhihuHotItem> items) {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.showLeastContent(items);
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
                        mPresenter.getHotList();
                    }
                }).show();
    }
}
