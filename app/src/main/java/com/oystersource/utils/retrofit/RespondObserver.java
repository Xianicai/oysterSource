package com.oystersource.utils.retrofit;

import com.elvishew.xlog.XLog;
import com.oystersource.base.BaseBean;
import com.oystersource.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Zhanglibin on 2017/11/16.
 */

public class RespondObserver<T extends BaseBean> implements Observer<T> {
    private static final String TAG = "RespondObserver";

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if (t.isSuccess()) {
            onSuccess(t);
        } else {
            onFailure(t);
            ToastUtil.INSTANCE.showMessage(t.getMessage());
        }
    }


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            XLog.e(TAG, "onError() called with: " + "throwable = [" + e + "], b = [" + e.getMessage() + "]");
        }
        onErrored(e);
    }

    @Override
    public void onComplete() {
        onFinish();
    }


    public void onSuccess(T result) {
    }

    public void onFailure(T result) {
    }

    public void onFinish() {
    }

    public void onErrored(Throwable exception) {
    }
}
