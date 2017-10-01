package com.example.lichongyang.look.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by lichongyang on 2017/9/27.
 */

public abstract class BaseFragment extends SupportFragment{
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    protected boolean isInited = false;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity)context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        isInited = true;
        setupView();
    }

    protected abstract void setupView();

    protected abstract void initView(View view);

    protected abstract int getLayoutId();
}
