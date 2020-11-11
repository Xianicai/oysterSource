package com.oystersource.person.presenter

import com.oystersource.base.presenter.BasePresenterImpl
import com.oystersource.person.model.LoginMode
import com.oystersource.person.model.bean.LoginBean
import com.oystersource.person.presenter.contract.LoginContract
import com.oystersource.person.presenter.contract.SourceContract
import com.oystersource.utils.retrofit.RespondObserver
import com.oystersource.utils.retrofit.ThreadTransformer

class LoginPresenterImpl : BasePresenterImpl<LoginContract.LoginView>(), LoginContract.LoginPresenter {

    private var loginMode = LoginMode()
    override fun login(phone: String, password: String) {

        loginMode.login(phone,password)
            .compose(ThreadTransformer<LoginBean>())
            .subscribe(object :RespondObserver<LoginBean>(){
                override fun onSuccess(result: LoginBean?) {
                    super.onSuccess(result)
                    mView?.login(result)
                }
            })
    }


}
