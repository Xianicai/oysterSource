package com.oystersource.person.presenter

import com.oystersource.base.BaseApplication
import com.oystersource.base.presenter.BasePresenterImpl
import com.oystersource.person.model.bean.UserBean
import com.oystersource.person.model.PersonModel
import com.oystersource.person.presenter.contract.PersonContract
import com.oystersource.utils.SharedPreferencesHelper
import com.oystersource.utils.retrofit.RespondObserver
import com.oystersource.utils.retrofit.ThreadTransformer

class PersonPresenterImpl : BasePresenterImpl<PersonContract.UserView>(),
    PersonContract.UserPresenter {

    private var loginModelImpl: PersonModel = PersonModel()


    override fun getUserDetail() {
        var userId = ""
        val preferencesHelper = SharedPreferencesHelper(BaseApplication.instance, "LoginBean")
        userId = preferencesHelper.getSharedPreference("userId", userId).toString()
        loginModelImpl.getUserDetail(userId)
            .compose(ThreadTransformer<UserBean>())
            .subscribe(object : RespondObserver<UserBean>() {
                override fun onSuccess(result: UserBean?) {
                    super.onSuccess(result)
                    mView?.getUserDetail(result)
                }
            })
    }
}
