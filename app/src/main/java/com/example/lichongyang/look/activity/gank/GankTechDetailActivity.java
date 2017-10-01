package com.example.lichongyang.look.activity.gank;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lichongyang.look.MyApplication;
import com.example.lichongyang.look.R;
import com.example.lichongyang.look.base.Constants;
import com.example.lichongyang.look.utils.NetWorkUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by lichongyang on 2017/10/1.
 */

public class GankTechDetailActivity extends AppCompatActivity{
    private WebView mWebView;
    private TextView mTextView;
    private Toolbar mToolbar;

    private MenuItem menuItem;
    private boolean isLiked;

    private String title;
    private String url;
    private String imgUrl;
    private String id;
    private int code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_tech_detail);
        init();
        setupView();
    }

    private void init(){
        mTextView = (TextView)findViewById(R.id.tv_tech_detail_progress);
        mWebView = (WebView)findViewById(R.id.wv_tech_detail);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
    }

    private void setupView(){
        Intent intent = getIntent();
        title = intent.getExtras().getString(Constants.GANK_TECH_TITLE);
        url = intent.getExtras().getString(Constants.GANK_TECH_URL);
        imgUrl = intent.getExtras().getString(Constants.GANK_TECH_IMAGE_URL);
        id = intent.getExtras().getString(Constants.GANK_TECH_ID);
        code = intent.getExtras().getInt(Constants.GANK_TECH_CODE);

        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final WebSettings webSettings = mWebView.getSettings();
        webSettings.setBlockNetworkImage(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        if (NetWorkUtils.isNetWorkAvailable(this)){
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }else{
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setSupportZoom(true);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (mTextView == null){
                    return;
                }
                if (i == 100){
                    mTextView.setVisibility(View.GONE);
                }else{
                    mTextView.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams lp = mTextView.getLayoutParams();
                    lp.width = MyApplication.SCREEN_WIDTH * i / 100;
                }
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                setTitle(s);
            }
        });
        mWebView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.girl_menu, menu);
        menuItem = menu.findItem(R.id.action_like);
        setLikeState(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_like:
                break;
            case R.id.action_save:
                break;
            case R.id.action_share:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setLikeState(boolean likeState) {
        if (likeState){
            menuItem.setIcon(R.mipmap.ic_toolbar_like_p);
            isLiked = true;
        }else{
            menuItem.setIcon(R.mipmap.ic_toolbar_like_n);
            isLiked = false;
        }
    }

    public static class Builder{
        private String title;
        private String url;
        private String imgUrl;
        private String id;
        private int code;
        private View shareView;
        private Context context;
        private Activity activity;

        public Builder(){}

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }


        public Builder setAnimCofig(Activity activity, View shareView){
            this.activity = activity;
            this.shareView = shareView;
            return this;
        }


        public void launch(){
            if (shareView != null){
                Intent intent = new Intent();
                intent.setClass(context, GankTechDetailActivity.class);
                intent.putExtra(Constants.GANK_TECH_TITLE, title);
                intent.putExtra(Constants.GANK_TECH_URL, url);
                intent.putExtra(Constants.GANK_TECH_IMAGE_URL, imgUrl);
                intent.putExtra(Constants.GANK_TECH_ID, id);
                intent.putExtra(Constants.GANK_TECH_CODE, code);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, shareView, "shareView");
                context.startActivity(intent, options.toBundle());
            }else{
                Intent intent = new Intent();
                intent.setClass(context, GankTechDetailActivity.class);
                intent.putExtra(Constants.GANK_TECH_TITLE, title);
                intent.putExtra(Constants.GANK_TECH_URL, url);
                intent.putExtra(Constants.GANK_TECH_IMAGE_URL, imgUrl);
                intent.putExtra(Constants.GANK_TECH_ID, id);
                intent.putExtra(Constants.GANK_TECH_CODE, code);
                context.startActivity(intent);
            }
        }
    }
}
