package cn.flyexp.douban_movie.presenter;

import android.util.Log;

import cn.flyexp.douban_movie.base.BasePresenter;
import cn.flyexp.douban_movie.model.CelebrityDetailModel;
import cn.flyexp.douban_movie.net.NetWork;
import cn.flyexp.douban_movie.presenter.ipresenter.ICelebrityDetailPresenter;
import cn.flyexp.douban_movie.view.iview.ICelebrityDetailView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Won on 2017/3/15.
 */

public class CelebrityDetailPresenter extends BasePresenter<ICelebrityDetailView> implements ICelebrityDetailPresenter {

    private static final String TAG = "CelebrityDetailPresente";

    private Observer<CelebrityDetailModel> observer = new Observer<CelebrityDetailModel>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError: ", e);
            mView.showError();
        }

        @Override
        public void onNext(CelebrityDetailModel celebrityDetailModel) {
            mView.showComplete(celebrityDetailModel);
        }
    };


    @Override
    public void loadingData(String id) {
        mView.showLoading();
        subscription = NetWork.getDouBanApi()
                .getCelebrityDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    protected void onDestroy() {
        unsubscribe();
    }
}
