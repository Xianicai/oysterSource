package com.oystersource.person.presenter.contract

import com.oystersource.base.view.BaseView
import com.oystersource.person.model.bean.LoginBean

interface LoginContract {

    interface LoginView : BaseView {
        fun login(bean: LoginBean?)
    }

    interface LoginPresenter {
        fun login( phone: String,password: String)
    }
}