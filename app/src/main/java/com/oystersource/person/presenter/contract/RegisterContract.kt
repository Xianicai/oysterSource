package com.oystersource.person.presenter.contract

import com.oystersource.base.BaseBean
import com.oystersource.base.view.BaseView

interface RegisterContract {

    interface RegisterView : BaseView {
        fun registerUser(bean: BaseBean?)
    }

    interface RegisterPresenter {
        fun registerUser(name: String, password: String, phone: String, role: String)
    }
}