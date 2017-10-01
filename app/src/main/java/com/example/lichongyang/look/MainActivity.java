package com.example.lichongyang.look;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.lichongyang.look.base.BaseActivity;
import com.example.lichongyang.look.fragment.gank.GankMainFragment;
import com.example.lichongyang.look.fragment.zhihu.ZhihuMainFragment;
import com.example.lichongyang.look.utils.ActivityUtils;

import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    Toolbar mToolbar;

    ActionBarDrawerToggle mToggle;
    MenuItem mLastMenuItem;

    GankMainFragment gankMainFragment;
    ZhihuMainFragment zhihuMainFragment;

    SupportFragment showFragment;
    SupportFragment hideFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setupView() {
        setToolbar(mToolbar, "干货");
        gankMainFragment = new GankMainFragment();
        zhihuMainFragment = new ZhihuMainFragment();
        hideFragment = gankMainFragment;
        mLastMenuItem = mNavigationView.getMenu().findItem(R.id.item_gank);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
        loadMultipleRootFragment(R.id.fragemt_container, 0, gankMainFragment, zhihuMainFragment);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                transaction(item);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }


    @Override
    public void initView() {
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        mNavigationView = (NavigationView)findViewById(R.id.navigation);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
    }

    private void transaction(MenuItem item){
        switch (item.getItemId()){
            case R.id.item_gank:
                showFragment = gankMainFragment;
                break;
            case R.id.item_zhihu:
                showFragment = zhihuMainFragment;
                break;
        }
        if (showFragment != null) {
            showHideFragment(showFragment, hideFragment);
            mToolbar.setTitle(item.getTitle());
        }
        mLastMenuItem.setChecked(false);
        mLastMenuItem = item;
        item.setChecked(true);
        hideFragment = showFragment;
    }

}
