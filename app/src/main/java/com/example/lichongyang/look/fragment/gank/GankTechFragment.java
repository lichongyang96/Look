package com.example.lichongyang.look.fragment.gank;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lichongyang.look.R;
import com.example.lichongyang.look.activity.gank.GankTechDetailActivity;
import com.example.lichongyang.look.adapter.gank.GankTechAdapter;
import com.example.lichongyang.look.base.BaseFragment;
import com.example.lichongyang.look.base.Constants;
import com.example.lichongyang.look.contract.gank.TechContract;
import com.example.lichongyang.look.model.bean.gank.MeiziBean;
import com.example.lichongyang.look.model.bean.gank.TechBean;
import com.example.lichongyang.look.presenter.gank.TechPresenter;
import com.example.lichongyang.look.utils.PiexlUtils;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by lichongyang on 2017/9/27.
 */

public class GankTechFragment extends BaseFragment implements TechContract.View{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AppBarLayout mAppBarLayout;
    private ImageView techBlurImageView;
    private ImageView techOriginIamgeView;
    private TextView techCopyrightTextView;
    private RecyclerView mRecyclerView;

    private TechContract.Presenter mPresenter;
    private GankTechAdapter mAdapter;

    private String techType;
    private int techCode;

    private boolean isLoadingMore = false;
    private int index = 1;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new TechPresenter(this, techType);
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
    protected int getLayoutId() {
        return R.layout.fragment_gank_tech;
    }

    @Override
    protected void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        mAppBarLayout = (AppBarLayout)view.findViewById(R.id.appbar_tech);
        techBlurImageView = (ImageView)view.findViewById(R.id.iv_tech_blur);
        techOriginIamgeView = (ImageView)view.findViewById(R.id.iv_tech_origin);
        techCopyrightTextView = (TextView)view.findViewById(R.id.tv_tech_copyright);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycle_view_tech);
    }

    @Override
    protected void setupView() {
        techType = getArguments().getString(Constants.GANK_TECH_TYPE);
        techCode = getArguments().getInt(Constants.GANK_TECH_CODE);
        mAdapter = new GankTechAdapter(mContext, techType);
        mAdapter.setmOnItemClickListener(new GankTechAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, TechBean data) {
                new GankTechDetailActivity.Builder()
                        .setContext(mContext)
                        .setId(data.get_id())
                        .setTitle(data.getDesc())
                        .setUrl(data.getUrl())
                        .setCode(techCode)
                        .setAnimCofig(mActivity, view)
                        .launch();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
                int totalItemCount = mRecyclerView.getLayoutManager().getItemCount();
                if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {  //还剩2个Item时加载更多
                    if(!isLoadingMore){
                        isLoadingMore = true;
                        index++;
                        mPresenter.getMoreTech(index, techType);
                    }
                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setProgressViewOffset(true, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        100, getResources().getDisplayMetrics()));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                index = 1;
                mPresenter.getLeastTech(techType);
                mPresenter.getMeiziImage();
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                    float rate = (float)(PiexlUtils.dp2px(mContext, 256) + verticalOffset * 2) / PiexlUtils.dp2px(mContext, 256);
                    if (rate >= 0)
                        techOriginIamgeView.setAlpha(rate);
                }
            }
        });

    }

    @Override
    public void setPresenter(TechContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLeastTech(List<TechBean> techList) {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.showLeast(techList);
    }

    @Override
    public void showMoreTech(List<TechBean> techList) {
        isLoadingMore = false;
        mAdapter.showMore(techList);
    }

    @Override
    public void showMeiziImage(List<MeiziBean> meiziList) {
        Glide.with(mContext).load(meiziList.get(0).getUrl())
                .crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(techOriginIamgeView);
        Glide.with(mContext).load(meiziList.get(0).getUrl())
                .bitmapTransform(new BlurTransformation(mContext, null)).into(techBlurImageView);
        techCopyrightTextView.setText(String.format("by: %s", meiziList.get(0).getWho()));
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
                        mPresenter.getLeastTech(techType);
                    }
                }).show();
    }
}
