package com.oystersource.person.presenter.contract

import com.oystersource.base.BaseBean
import com.oystersource.base.view.BaseView
import com.oystersource.person.model.bean.LoginBean

interface SourceContract {

    interface SourceView : BaseView {
        fun getSourceInfo(bean: BaseBean?)
    }

    interface SourcePresenter {
        fun getSourceInfo()
    }
}