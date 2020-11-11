package com.oystersource.home.presenter.Contract

import com.oystersource.base.BaseBean
import com.oystersource.base.view.BaseView
import com.oystersource.home.model.bean.OysterBean
import com.oystersource.person.model.bean.UserBean

interface BreedContract {

    interface BreedView : BaseView {
        fun breedOperate(bean: BaseBean?)
    }

    interface BreedPresenter {
        fun breedOperate(
            number: String?,
            label: String?,
            longitude: String?,
            latitude: String?,
            amount: String?,
            type: String?,
            imgPathList: String?,
            checkName: String?,
            checkPhone: String?
        )
    }
}