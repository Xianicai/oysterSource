package com.oystersource.home.presenter.Contract

import com.oystersource.base.BaseBean
import com.oystersource.base.view.BaseView
import com.oystersource.home.model.bean.OysterBean
import com.oystersource.person.model.bean.UserBean

interface AddOysterContract {

    interface AddOysterView : BaseView {

        fun addOyster(bean: OysterBean?)

    }
    interface AddOysterPresenter {
        fun addOyster(amount: String?, type: String?, state: String?, imgPathList: List<String>?)
    }
}