package com.example.lichongyang.look.fragment.gank;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lichongyang.look.R;
import com.example.lichongyang.look.activity.gank.GankGirlDetailActivity;
import com.example.lichongyang.look.adapter.gank.GankGirlAdapter;
import com.example.lichongyang.look.base.BaseFragment;
import com.example.lichongyang.look.base.Constants;
import com.example.lichongyang.look.contract.gank.MeiziContract;
import com.example.lichongyang.look.model.bean.gank.MeiziBean;
import com.example.lichongyang.look.presenter.gank.MeiziPresenter;

import java.util.List;

/**
 * todo:瀑布流图片闪烁问题
 * Created by lichongyang on 2017/9/27.
 */

public class GankGirlFragment extends BaseFragment implements MeiziContract.View {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private GankGirlAdapter mAdapter;

    private static final int SPAN_COUNT = 2;
    private boolean isLoadingMore = false;

    private MeiziContract.Presenter mPresenter;

    private int index = 1;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new MeiziPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_common_list;
    }

    @Override
    protected void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycle_view);
    }

    @Override
    protected void setupView() {
        mAdapter = new GankGirlAdapter(mContext);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mStaggeredGridLayoutManager.setItemPrefetchEnabled(false);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setProgressViewOffset(true, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        100, getResources().getDisplayMetrics()));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                index = 1;
                mPresenter.getLeastImage();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] visibleItems = mStaggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(null);
                int lastItem = Math.max(visibleItems[0], visibleItems[1]);
                if (lastItem > mAdapter.getItemCount() - 5 && !isLoadingMore && dy > 0){
                    isLoadingMore = true;
                    index++;
                    mPresenter.getMoreImage(index);
                }
            }
        });
        mAdapter.setOnItemClickListener(new GankGirlAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, MeiziBean data) {
                Intent intent = new Intent();
                intent.setClass(mContext, GankGirlDetailActivity.class);
                intent.putExtra(Constants.GANK_GIRL_URL, data.getUrl());
                intent.putExtra(Constants.GANK_GIRL_ID, data.get_id());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, "shareView");
                mContext.startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public void setPresenter(MeiziContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLeastImage(List<MeiziBean> meiziList) {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.showLeast(meiziList);

    }

    @Override
    public void showMoreImage(List<MeiziBean> meiziList) {
        isLoadingMore = false;
        mAdapter.showMore(meiziList);
    }

    @Override
    public void showError(String msg) {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        Snackbar.make(mRecyclerView, getString(R.string.error_message), Snackbar.LENGTH_SHORT)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.getLeastImage();
                    }
                }).show();
    }
}
