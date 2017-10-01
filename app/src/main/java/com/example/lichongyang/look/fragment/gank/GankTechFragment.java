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

public class GankTechFragment extends Fragment implements TechContract.View{
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gank_tech, container, false);
        initView(root);
        setupView();
        mPresenter = new TechPresenter(this, techType);
        return root;
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
    public void initView(View root) {
        mSwipeRefreshLayout = (SwipeRefreshLayout)root.findViewById(R.id.swipe_refresh);
        mAppBarLayout = (AppBarLayout)root.findViewById(R.id.appbar_tech);
        techBlurImageView = (ImageView)root.findViewById(R.id.iv_tech_blur);
        techOriginIamgeView = (ImageView)root.findViewById(R.id.iv_tech_origin);
        techCopyrightTextView = (TextView)root.findViewById(R.id.tv_tech_copyright);
        mRecyclerView = (RecyclerView)root.findViewById(R.id.recycle_view_tech);
    }

    @Override
    public void setupView() {
        techType = getArguments().getString(Constants.GANK_TECH_TYPE);
        techCode = getArguments().getInt(Constants.GANK_TECH_CODE);
        mAdapter = new GankTechAdapter(getContext(), techType);
        mAdapter.setmOnItemClickListener(new GankTechAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, TechBean data) {
                new GankTechDetailActivity.Builder()
                        .setContext(getContext())
                        .setId(data.get_id())
                        .setTitle(data.getDesc())
                        .setUrl(data.getUrl())
                        .setCode(techCode)
                        .setAnimCofig((Activity)getContext(), view)
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
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
                    float rate = (float)(PiexlUtils.dp2px(getContext(), 256) + verticalOffset * 2) / PiexlUtils.dp2px(getContext(), 256);
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
        Glide.with(getContext()).load(meiziList.get(0).getUrl())
                .crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(techOriginIamgeView);
        Glide.with(getContext()).load(meiziList.get(0).getUrl())
                .bitmapTransform(new BlurTransformation(getContext(), null)).into(techBlurImageView);
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
