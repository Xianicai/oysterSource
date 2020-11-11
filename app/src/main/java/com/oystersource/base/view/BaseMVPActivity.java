package com.oystersource.base.view;


import android.os.Bundle;
import com.oystersource.base.presenter.BasePresenter;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

/**
 * ZY:基础的Aty,实现简单的接口，方法
 * Created by zhanglibin.
 */
public abstract class BaseMVPActivity<T extends BasePresenter> extends RxFragmentActivity implements BaseView {
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(getLayoutId());
        QMUIStatusBarHelper.translucent(this);
        initPresenter();
        //初始化控件
        initViews(savedInstanceState);
    }

    public void initWindow() {
    }

    protected void initPresenter() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }


    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    public abstract int getLayoutId();

    /**
     * 创建一个Presenter
     *
     * @return
     */
    protected abstract T createPresenter();

    public abstract void initViews(Bundle savedInstanceState);

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
