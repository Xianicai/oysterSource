package com.oystersource.source.presenter

import com.oystersource.base.BaseBean
import com.oystersource.base.presenter.BasePresenterImpl
import com.oystersource.person.model.LoginMode
import com.oystersource.person.model.bean.LoginBean
import com.oystersource.person.presenter.contract.SourceContract
import com.oystersource.source.model.SourceMode
import com.oystersource.utils.retrofit.RespondObserver
import com.oystersource.utils.retrofit.ThreadTransformer

class SourceInfoPresenterImpl : BasePresenterImpl<SourceContract.SourceView>(),
    SourceContract.SourcePresenter {

    private var mode = SourceMode()
    override fun getSourceInfo() {

        mode.getSourceInfo()
            .compose(ThreadTransformer<BaseBean>())
            .subscribe(object : RespondObserver<BaseBean>() {
                override fun onSuccess(result: BaseBean?) {
                    super.onSuccess(result)
                    mView?.getSourceInfo(result)
                }
            })
    }


}
