package com.oystersource.home.presenter.Contract

import com.oystersource.base.BaseBean
import com.oystersource.base.view.BaseView
import com.oystersource.home.model.bean.OysterBean
import com.oystersource.person.model.bean.UserBean

interface AddSeedContract {

    interface AddSeedView : BaseView {

        fun addOperate(bean: BaseBean?)

    }
    interface AddSeedPresenter {
        fun addOperate(  number: String?,
                         type: String?,
                         longitude: String?,
                         latitude: String?,
                         checkName: String?,
                         checkPhone: String?,
                         oysterIdList: String?)
    }
}