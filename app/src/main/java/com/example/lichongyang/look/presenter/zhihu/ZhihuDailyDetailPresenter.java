package com.example.lichongyang.look.presenter.zhihu;

import android.util.Log;

import com.example.lichongyang.look.contract.zhihu.ZhihuDailyDetailContract;
import com.example.lichongyang.look.model.ApiManager;
import com.example.lichongyang.look.model.api.ZhihuApi;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDailyDetail;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDailyDetailExtra;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lichongyang on 2017/10/5.
 */

public class ZhihuDailyDetailPresenter implements ZhihuDailyDetailContract.Presenter{
    private ZhihuDailyDetailContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    public ZhihuDailyDetailPresenter(ZhihuDailyDetailContract.View view){
        this.mView = view;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        getContent(mView.getId());
        getExtraMessage(mView.getId());
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void getContent(int id) {
        Log.d("TAG", "" + id);
        ApiManager.getInstance().getService(ZhihuApi.class).getDailyDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuDailyDetail>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ZhihuDailyDetail zhihuDailyDetail) {
                        mView.showContent(zhihuDailyDetail);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getExtraMessage(int id) {
        ApiManager.getInstance().getService(ZhihuApi.class).getDailyDetailExtra(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuDailyDetailExtra>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ZhihuDailyDetailExtra zhihuDailyDetailExtra) {
                        mView.showExtraMessage(zhihuDailyDetailExtra);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
