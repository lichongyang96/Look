package com.example.lichongyang.look.presenter.gank;

import com.example.lichongyang.look.contract.gank.TechContract;
import com.example.lichongyang.look.model.ApiManager;
import com.example.lichongyang.look.model.api.GankApi;
import com.example.lichongyang.look.model.bean.gank.Meizi;
import com.example.lichongyang.look.model.bean.gank.Tech;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lichongyang on 2017/9/29.
 */

public class TechPresenter implements TechContract.Presenter{
    private TechContract.View techView;
    private CompositeDisposable mCompositeDisposable;

    private String currentType;

    public TechPresenter(TechContract.View view, String currentTypet){
        this.techView = view;
        this.currentType = currentTypet;
        techView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }


    @Override
    public void subscribe() {
        getLeastTech(currentType);
        getMeiziImage();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void getLeastTech(String type) {
        currentType = type;
        ApiManager.getInstance().getService(GankApi.class).getTech(type, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Tech>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Tech tech) {
                        techView.showLeastTech(tech.getResult());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        techView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getMoreTech(int index, String type) {
        ApiManager.getInstance().getService(GankApi.class).getTech(type, index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Tech>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Tech tech) {
                        techView.showMoreTech(tech.getResult());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        techView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getMeiziImage() {
        ApiManager.getInstance().getService(GankApi.class).getRandomMeizi(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Meizi>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Meizi meizi) {
                        techView.showMeiziImage(meizi.getResult());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        techView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
