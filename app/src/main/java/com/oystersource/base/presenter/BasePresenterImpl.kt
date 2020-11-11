package com.oystersource.base.presenter


import com.oystersource.base.view.BaseView

/**
 * Created by Zhanglibin on 2017/4/8.
 */

open class BasePresenterImpl<T : BaseView> : BasePresenter<T> {
    override val isViewAttached: Boolean
        get() = mView != null

    protected var mView: T? = null

    override fun attachView(view: T) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }


}
