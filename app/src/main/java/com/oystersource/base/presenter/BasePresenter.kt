package com.oystersource.base.presenter

import com.oystersource.base.view.BaseView

/**
 * Created by Zhanglibin on 2017/4/19.
 */

interface BasePresenter<T : BaseView> {

    /**
     * 判断View是否已经销毁
     *
     * @return
     */
    val isViewAttached: Boolean

    /**
     * 依附生命view
     *
     * @param view
     */
    fun attachView(view: T)

    /**
     * 分离View
     */
    fun detachView()

}
