package com.oystersource.person.presenter

import com.oystersource.base.BaseBean
import com.oystersource.base.presenter.BasePresenterImpl
import com.oystersource.person.model.RegisterMode
import com.oystersource.person.presenter.contract.RegisterContract
import com.oystersource.utils.retrofit.RespondObserver
import com.oystersource.utils.retrofit.ThreadTransformer

class RegisterPresenterImpl : BasePresenterImpl<RegisterContract.RegisterView>(), RegisterContract.RegisterPresenter {

    private var registerMode = RegisterMode()

    override fun registerUser(name: String, password: String, phone: String, role: String) {
        registerMode.registerUser(name, password, phone, role)
            .compose(ThreadTransformer<BaseBean>())
            .subscribe(object :RespondObserver<BaseBean>(){
                override fun onSuccess(result: BaseBean?) {
                    super.onSuccess(result)
                    mView?.registerUser(result)
                }
            })
    }
}
