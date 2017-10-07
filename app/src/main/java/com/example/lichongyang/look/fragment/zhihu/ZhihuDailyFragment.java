package com.example.lichongyang.look.fragment.zhihu;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.lichongyang.look.R;
import com.example.lichongyang.look.adapter.zhihu.ZhihuDailyAdapter;
import com.example.lichongyang.look.base.BaseFragment;
import com.example.lichongyang.look.contract.zhihu.ZhihuDailyContract;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDaily;
import com.example.lichongyang.look.presenter.gank.MeiziPresenter;
import com.example.lichongyang.look.presenter.zhihu.ZhihuDailyPresenter;

/**
 * Created by lichongyang on 2017/10/1.
 */

public class ZhihuDailyFragment extends BaseFragment implements ZhihuDailyContract.View{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private ZhihuDailyContract.Presenter mPresenter;

    private ZhihuDailyAdapter mAdapter;
    private ZhihuDaily timeLine;
    private LinearLayoutManager mLinearLayoutManager;
    private String currentTime;
    private int lastVisibleItem;
    private boolean isLoadMore = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new ZhihuDailyPresenter(this);
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
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {
        this.mPresenter = presenter;
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
                        mPresenter.getLeastNews();
                    }
                }).show();
    }

    @Override
    public void displayZhihuNews(ZhihuDaily zhihuDaily) {
        Log.d("ZHIHU", zhihuDaily.getDate());
        if (isLoadMore){
            if (currentTime == null){
                mAdapter.updateStatus(mAdapter.LOAD_NONE);
                return;
            }else{
                timeLine.getZhihuDailyItems().addAll(zhihuDaily.getZhihuDailyItems());
            }
            mAdapter.notifyDataSetChanged();
        }else{
            timeLine = zhihuDaily;
            mAdapter = new ZhihuDailyAdapter(mContext, zhihuDaily);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        currentTime = zhihuDaily.getDate();
    }

    @Override
    protected void setupView() {
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mSwipeRefreshLayout.setProgressViewOffset(true, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        100, getResources().getDisplayMetrics()));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getLeastNews();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                    if (mLinearLayoutManager.getItemCount() == 1){
                        mAdapter.updateStatus(mAdapter.LOAD_NONE);
                        return;
                    }
                    if (lastVisibleItem + 1 == mLinearLayoutManager.getItemCount()){
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.updateStatus(mAdapter.LOAD_PULL_TO);
                        isLoadMore = true;
                        mAdapter.updateStatus(mAdapter.LOAD_MORE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPresenter.getBeforeNews(currentTime);
                            }
                        }, 1000);
                    }
                }
            }
        });
    }

    @Override
    protected void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycle_view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_common_list;
    }
}
