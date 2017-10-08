package com.example.lichongyang.look.presenter.zhihu;

import com.example.lichongyang.look.contract.zhihu.ZhihuHotContract;
import com.example.lichongyang.look.model.ApiManager;
import com.example.lichongyang.look.model.api.ZhihuApi;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuHot;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lichongyang on 2017/10/7.
 */

public class ZhihuHotPresenter implements ZhihuHotContract.Presenter{
    private ZhihuHotContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    public ZhihuHotPresenter(ZhihuHotContract.View view){
        this.mView = view;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        getHotList();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void getHotList() {
        ApiManager.getInstance().getService(ZhihuApi.class).getHot()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuHot>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ZhihuHot zhihuHot) {
                        mView.showHotList(zhihuHot.getItems());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
