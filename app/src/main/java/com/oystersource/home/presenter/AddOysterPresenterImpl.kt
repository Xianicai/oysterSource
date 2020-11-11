package com.oystersource.home.presenter

import com.oystersource.base.presenter.BasePresenterImpl
import com.oystersource.home.model.AddOysterMode
import com.oystersource.home.model.bean.OysterBean
import com.oystersource.home.presenter.Contract.AddOysterContract
import com.oystersource.utils.retrofit.RespondObserver
import com.oystersource.utils.retrofit.ThreadTransformer

class AddOysterPresenterImpl : BasePresenterImpl<AddOysterContract.AddOysterView>(),
    AddOysterContract.AddOysterPresenter {

    private var mode = AddOysterMode()
    override fun addOyster(amount: String?, type: String?, state: String?, imgPathList: List<String>?) {
        amount ?: return
        type ?: return
        state ?: return
        imgPathList ?: return
        mode.addOyster(amount,type,state,imgPathList)
            .compose(ThreadTransformer<OysterBean>())
            .subscribe(object : RespondObserver<OysterBean>() {
                override fun onSuccess(result: OysterBean?) {
                    super.onSuccess(result)
                    mView?.addOyster(result)
                }
            })
    }




}
