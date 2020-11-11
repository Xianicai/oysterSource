package com.oystersource.home.presenter

import com.oystersource.base.BaseBean
import com.oystersource.base.presenter.BasePresenterImpl
import com.oystersource.home.model.AddFarmerMode
import com.oystersource.home.presenter.Contract.AddFarmerContract
import com.oystersource.utils.retrofit.RespondObserver
import com.oystersource.utils.retrofit.ThreadTransformer

class AddFarmerPresenterImpl : BasePresenterImpl<AddFarmerContract.AddFarmerView>(),
    AddFarmerContract.AddFarmerPresenter {

    private var mode = AddFarmerMode()
    override fun addFarmer(
        name: String?,
        phone: String?,
        address: String?,
        breedYear: String?,
        raftCount: Int?, //阀数
        farmNumber: String?, //编号
        breedArea: String?,//面积
        breadProduct: String?, //产量
        longitude: String?,
        latitude: String?,
    ) {
        mode.addFarmer(
            name,
            phone,
            address,
            breedYear,
            raftCount,
            farmNumber,
            breedArea,
            breadProduct,
            longitude,
            latitude,
        )
            .compose(ThreadTransformer<BaseBean>())
            .subscribe(object : RespondObserver<BaseBean>() {
                override fun onSuccess(result: BaseBean?) {
                    super.onSuccess(result)
                    mView?.addFarmer(result)
                }
            })
    }


}
