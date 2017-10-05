package com.example.lichongyang.look.activity.zhihu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lichongyang.look.R;
import com.example.lichongyang.look.base.BaseActivity;
import com.example.lichongyang.look.base.Constants;
import com.example.lichongyang.look.contract.zhihu.ZhihuDailyDetailContract;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDailyDetail;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDailyDetailExtra;
import com.example.lichongyang.look.presenter.zhihu.ZhihuDailyDetailPresenter;
import com.example.lichongyang.look.utils.HtmlUtils;
import com.example.lichongyang.look.utils.NetWorkUtils;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

/**
 * Created by lichongyang on 2017/10/5.
 */

public class ZhihuDailyDetailActivity extends BaseActivity implements ZhihuDailyDetailContract.View{
    private ImageView topImageView;
    private TextView topCopyrightTextView;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private WebView webView;
    private NestedScrollView nestedScrollView;
    private TextView likeTextView;
    private TextView commentTextView;
    private TextView shareTextView;
    private FrameLayout bottomFrameLayout;

    private int id = -1;
    private String imageUrl = "";
    private String shareUrl = "";
    private boolean isBottomShow = true;

    private ZhihuDailyDetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ZhihuDailyDetailPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhihu_daily_detail;
    }

    @Override
    protected void initView() {
        topImageView = (ImageView)findViewById(R.id.iv_daily_detail_top);
        topCopyrightTextView = (TextView)findViewById(R.id.tv_daily_detail_top);
        toolbar = (Toolbar)findViewById(R.id.toolbar_daily_detail);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.clp_toolbar_daily_detail);
        webView = (WebView)findViewById(R.id.wv_daily_detail);
        nestedScrollView = (NestedScrollView)findViewById(R.id.nsv_scroller_daily_detail);
        likeTextView = (TextView)findViewById(R.id.tv_daily_detail_like);
        commentTextView = (TextView)findViewById(R.id.tv_daily_detail_comment);
        shareTextView = (TextView)findViewById(R.id.tv_daily_detail_share);
        bottomFrameLayout = (FrameLayout)findViewById(R.id.ll_daily_detail_bottom);
    }

    @Override
    protected void setupView() {
        setToolbar(toolbar, "");
        Intent intent = getIntent();
        id = Integer.valueOf(intent.getExtras().getString(Constants.ZHIHU_DAILY_DETAIL_ID));
        WebSettings settings = webView.getSettings();
        if (NetWorkUtils.isNetWorkAvailable(this)){
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }else{
            settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY - oldScrollY > 0 && isBottomShow){
                    isBottomShow = false;
                    bottomFrameLayout.animate().translationY(bottomFrameLayout.getHeight());
                }else if (scrollY - oldScrollY < 0 && !isBottomShow){
                    isBottomShow = true;
                    bottomFrameLayout.animate().translationY(0);
                }
            }
        });
    }

    @Override
    public void setPresenter(ZhihuDailyDetailContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showContent(ZhihuDailyDetail zhihuDailyDetail) {
        imageUrl = zhihuDailyDetail.getImage();
        shareUrl = zhihuDailyDetail.getShare_url();
        Glide.with(mContext).load(imageUrl).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(topImageView);
        collapsingToolbarLayout.setTitle(zhihuDailyDetail.getTitle());
        topCopyrightTextView.setText(zhihuDailyDetail.getImage_source());
        String htmlData = HtmlUtils.createHtmlData(zhihuDailyDetail.getBody(), zhihuDailyDetail.getCss(), zhihuDailyDetail.getJs());
        webView.loadData(htmlData, HtmlUtils.MIME_TYPE, HtmlUtils.ENCODING);
    }

    @Override
    public void showExtraMessage(ZhihuDailyDetailExtra zhihuDailyDetailExtra) {
        likeTextView.setText(String.format("%d个赞", zhihuDailyDetailExtra.getPopularity()));
        commentTextView.setText(String.format("%d条评论", zhihuDailyDetailExtra.getComments()));

    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1){
            pop();
        }else{
            finishAfterTransition();
        }
    }

    @Override
    public int getId() {
        return id;
    }
}
