package com.oystersource.base.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oystersource.base.presenter.BasePresenter;
import com.trello.rxlifecycle2.components.support.RxFragment;


/**
 * Created by Zhanglibin on 2017/3/28.
 */

public abstract class BaseMVPFragment<T extends BasePresenter> extends RxFragment implements BaseView {
    protected T mPresenter;

    public abstract int getLayoutId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        int layoutId = getLayoutId();
        if (layoutId > 0) {
            return inflater.inflate(layoutId, container, false);
        }
        return super.onCreateView(inflater, container, state);
    }


    public BaseMVPFragment() {
        super();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initView(view);

    }

    protected void initPresenter() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected abstract void initView(View view);

    /**
     * 创建一个Presenter
     *
     * @return
     */
    protected abstract T createPresenter();


    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showProgress(String message) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String msg) {

    }
}
