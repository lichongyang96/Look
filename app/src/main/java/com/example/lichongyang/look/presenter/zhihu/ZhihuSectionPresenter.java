package com.example.lichongyang.look.presenter.zhihu;

import com.example.lichongyang.look.contract.zhihu.ZhihuSectionContract;
import com.example.lichongyang.look.model.ApiManager;
import com.example.lichongyang.look.model.api.ZhihuApi;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuSection;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lichongyang on 2017/10/7.
 */

public class ZhihuSectionPresenter implements ZhihuSectionContract.Presenter{
    private ZhihuSectionContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    public ZhihuSectionPresenter(ZhihuSectionContract.View view){
        this.mView = view;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        getSectionList();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void getSectionList() {
        ApiManager.getInstance().getService(ZhihuApi.class).getSections()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuSection>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ZhihuSection zhihuSection) {
                        mView.showSectionList(zhihuSection.getItems());
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
