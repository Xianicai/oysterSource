package com.oystersource.home.presenter

import com.oystersource.base.BaseBean
import com.oystersource.base.presenter.BasePresenterImpl
import com.oystersource.home.model.AddSeedMode
import com.oystersource.home.model.bean.OysterBean
import com.oystersource.home.presenter.Contract.AddSeedContract
import com.oystersource.utils.retrofit.RespondObserver
import com.oystersource.utils.retrofit.ThreadTransformer

class AddSeedPresenterImpl : BasePresenterImpl<AddSeedContract.AddSeedView>(),
    AddSeedContract.AddSeedPresenter {

    private var mode = AddSeedMode()
    override fun addOperate( number: String?,
                            type: String?,
                            longitude: String?,
                            latitude: String?,
                            checkName: String?,
                            checkPhone: String?,
                            oysterIdList: String?) {

        mode.addOperate( number,
            type,
            longitude,
            latitude,
            checkName,
            checkPhone,
            oysterIdList,)
            .compose(ThreadTransformer<BaseBean>())
            .subscribe(object : RespondObserver<BaseBean>() {
                override fun onSuccess(result: BaseBean?) {
                    super.onSuccess(result)
                    mView?.addOperate(result)
                }
            })
    }


}
