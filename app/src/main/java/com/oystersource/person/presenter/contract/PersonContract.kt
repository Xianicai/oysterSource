package com.oystersource.person.presenter.contract

import com.oystersource.base.view.BaseView
import com.oystersource.person.model.bean.UserBean

interface PersonContract {

    interface UserView : BaseView {
        fun getUserDetail(userBean: UserBean?)
    }

    interface UserPresenter {
        fun getUserDetail()
    }
}