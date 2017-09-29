package com.example.lichongyang.look.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lichongyang on 2017/9/27.
 */

public interface BaseView {
    public void initView(View root);
    public void setupView();
}
