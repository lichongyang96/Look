package com.example.lichongyang.look.presenter.zhihu;

import com.example.lichongyang.look.contract.zhihu.ZhihuThemeContentContract;
import com.example.lichongyang.look.model.ApiManager;
import com.example.lichongyang.look.model.api.ZhihuApi;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuThemeContent;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lichongyang on 2017/10/7.
 */

public class ZhihuThemeContentPresenter implements ZhihuThemeContentContract.Presenter{
    private ZhihuThemeContentContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    public ZhihuThemeContentPresenter(ZhihuThemeContentContract.View view){
        this.mView = view;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        getThemeContent(mView.getId());
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void getThemeContent(int id) {
        ApiManager.getInstance().getService(ZhihuApi.class).getThemeContent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuThemeContent>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ZhihuThemeContent zhihuThemeContent) {
                        mView.showThemeContent(zhihuThemeContent);
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
