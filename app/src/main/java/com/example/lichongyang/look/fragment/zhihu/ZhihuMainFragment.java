package com.example.lichongyang.look.fragment.zhihu;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.lichongyang.look.R;
import com.example.lichongyang.look.adapter.zhihu.ZhihuMainAdapter;
import com.example.lichongyang.look.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichongyang on 2017/10/1.
 */

public class ZhihuMainFragment extends BaseFragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public static String[] tabTitle = new String[]{"日报", "主题", "专栏", "热门"};
    private List<Fragment> fragments = new ArrayList<>();

    ZhihuMainAdapter mAdapter;

    @Override
    protected void setupView() {
        fragments.add(new ZhihuDailyFragment());
        fragments.add(new ZhihuThemeFragment());
        fragments.add(new ZhihuSectionFragment());
        fragments.add(new ZhihuHotFragment());

        mAdapter = new ZhihuMainAdapter(getChildFragmentManager(), fragments);
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

    @Override
    protected void initView(View view) {
        mTabLayout = (TabLayout)view.findViewById(R.id.tab_zhihu_main);
        mViewPager = (ViewPager)view.findViewById(R.id.vp_zhihu_main);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhihu_main;
    }
}
