package com.example.lichongyang.look.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by lichongyang on 2017/9/27.
 */

public class ActivityUtils {
    public static void addFragmentToActivity(FragmentManager manager, Fragment showFragment, Fragment hideFragment, int frameId){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(frameId, showFragment);
        transaction.hide(hideFragment);
        transaction.show(showFragment);
        transaction.commit();
    }
}
