package com.example.lichongyang.look.presenter.gank;

import android.content.Context;
import android.util.Log;

import com.example.lichongyang.look.contract.gank.MeiziContract;
import com.example.lichongyang.look.model.ApiManager;
import com.example.lichongyang.look.model.api.GankApi;
import com.example.lichongyang.look.model.bean.gank.Meizi;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by lichongyang on 2017/9/28.
 */

public class MeiziPresenter implements MeiziContract.Presenter{
    private final MeiziContract.View meiziView;
    private CompositeDisposable mCompositeDisposable;

    public MeiziPresenter(MeiziContract.View view){
        meiziView = view;
        meiziView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        getLeastImage();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void getLeastImage() {
        ApiManager.getInstance().getService(GankApi.class).getMeizi(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Meizi>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Meizi meizi) {
                        meiziView.showLeastImage(meizi.getResult());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        meiziView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getMoreImage(int index) {
        ApiManager.getInstance().getService(GankApi.class).getMeizi(index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Meizi>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Meizi meizi) {
                        meiziView.showMoreImage(meizi.getResult());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        meiziView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
