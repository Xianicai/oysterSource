package com.oystersource.home.presenter.Contract

import com.oystersource.base.BaseBean
import com.oystersource.base.view.BaseView
import com.oystersource.home.model.bean.OysterBean
import com.oystersource.person.model.bean.UserBean

interface AddFarmerContract {

    interface AddFarmerView : BaseView {

        fun addFarmer(bean: BaseBean?)

    }
    interface AddFarmerPresenter {
        fun addFarmer(name: String?,
                      phone: String?,
                      address: String?,
                      breedYear: String?,
                      raftCount: Int?, //阀数
                      farmNumber: String?, //编号
                      breedArea: String?,//面积
                      breadProduct: String?, //产量
                      longitude: String?,
                      latitude: String?,)
    }
}