package com.example.lichongyang.look.fragment.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lichongyang.look.R;
import com.example.lichongyang.look.adapter.gank.GankMainAdapter;
import com.example.lichongyang.look.base.BaseView;
import com.example.lichongyang.look.base.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * gank主页
 * Created by lichongyang on 2017/9/27.
 */

public class GankMainView extends Fragment implements BaseView {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private static String[] tabTitle = new String[]{"Android", "iOS", "前端", "妹子"};
    private List<Fragment> fragments = new ArrayList<>();

    GankMainAdapter mAdapter;
    GankTechFragment androidFragment;
    GankTechFragment iosFragment;
    GankTechFragment webFragment;
    GankGirlFragment girlFragment;

    public GankMainView(){}

    public static GankMainView getInstance(){
        return new GankMainView();
    }

    public String[] getTabTitle(){
        return tabTitle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gank_main, container, false);
        initView(root);
        setupView();
        return root;
    }

    public void initView(View root) {
        mTabLayout = (TabLayout)root.findViewById(R.id.tab_gank_main);
        mViewPager = (ViewPager)root.findViewById(R.id.vp_gank_main);
    }

    public void setupView(){


        androidFragment = new GankTechFragment();
        Bundle androidBundle = new Bundle();
        androidBundle.putString(Constants.GANK_TECH_TYPE, tabTitle[0]);
        androidBundle.putInt(Constants.GANK_TECH_CODE, Constants.TYPE_ANDROID);
        androidFragment.setArguments(androidBundle);
        fragments.add(androidFragment);

        iosFragment = new GankTechFragment();
        Bundle iosBundle = new Bundle();
        iosBundle.putString(Constants.GANK_TECH_TYPE, tabTitle[1]);
        iosBundle.putInt(Constants.GANK_TECH_CODE, Constants.TYPE_IOS);
        iosFragment.setArguments(iosBundle);
        fragments.add(iosFragment);

        webFragment = new GankTechFragment();
        Bundle webBundle = new Bundle();
        webBundle.putString(Constants.GANK_TECH_TYPE, tabTitle[2]);
        webBundle.putInt(Constants.GANK_TECH_CODE, Constants.TYPE_WEB);
        webFragment.setArguments(webBundle);
        fragments.add(webFragment);

        girlFragment = new GankGirlFragment();
        fragments.add(girlFragment);

        mAdapter = new GankMainAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);

        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[2]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[3]));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(tabTitle[0]);
        mTabLayout.getTabAt(1).setText(tabTitle[1]);
        mTabLayout.getTabAt(2).setText(tabTitle[2]);
        mTabLayout.getTabAt(3).setText(tabTitle[3]);


    }
}
