package com.oystersource.home.presenter.Contract

import com.oystersource.base.view.BaseView
import com.oystersource.person.model.bean.UserBean

interface HomeContract {

    interface HomeView : BaseView {

        fun getRecycleGold(recyclerGoldBean: UserBean)

    }
    interface HomePresenter {
        fun getRecycleGold()
    }
}