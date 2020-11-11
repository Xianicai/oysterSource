package com.oystersource.home.presenter

import com.oystersource.base.BaseBean
import com.oystersource.base.presenter.BasePresenterImpl
import com.oystersource.home.model.AddSeedMode
import com.oystersource.home.model.BreedMode
import com.oystersource.home.model.bean.OysterBean
import com.oystersource.home.presenter.Contract.AddSeedContract
import com.oystersource.home.presenter.Contract.BreedContract
import com.oystersource.utils.retrofit.RespondObserver
import com.oystersource.utils.retrofit.ThreadTransformer

class BreedPresenterImpl : BasePresenterImpl<BreedContract.BreedView>(),
    BreedContract.BreedPresenter {
    private var mode = BreedMode()
    override fun breedOperate(
        number: String?,
        label: String?,
        longitude: String?,
        latitude: String?,
        amount: String?,
        type: String?,
        imgPathList: String?,
        checkName: String?,
        checkPhone: String?
    ) {
        mode.breedOperate(
            number,
            label,
            longitude,
            latitude,
            amount,
            type,
            imgPathList,
            checkName,
            checkPhone
        )
            .compose(ThreadTransformer<BaseBean>())
            .subscribe(object : RespondObserver<BaseBean>() {
                override fun onSuccess(result: BaseBean?) {
                    super.onSuccess(result)
                    mView?.breedOperate(result)
                }
            })
    }


}
