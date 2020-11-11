package com.oystersource.base.view

/**
 * Created by Zhanglibin on 2017/4/8.
 */

interface BaseView {

    fun showProgress()

    fun showProgress(message: String)

    fun hideProgress()

    fun showMsg(msg: String)

}
