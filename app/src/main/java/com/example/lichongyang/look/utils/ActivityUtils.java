package com.example.lichongyang.look.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by lichongyang on 2017/9/27.
 */

public class ActivityUtils {
    public static void addFragmentToActivity(FragmentManager manager, Fragment fragment, int frameId){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
