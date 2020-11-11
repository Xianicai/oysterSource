package com.oystersource.home.model

import com.oystersource.base.BaseBean
import com.oystersource.home.model.bean.OysterBean
import com.oystersource.home.model.service.HomeService
import com.oystersource.utils.retrofit.Requester
import io.reactivex.Observable

interface IAddSeedMode {
    fun addOperate(
        number: String?,
        type: String?,
        longitude: String?,
        latitude: String?,
        checkName: String?,
        checkPhone: String?,
        oysterIdList: String?
    ): Observable<BaseBean>
}

class AddSeedMode : IAddSeedMode {
    private var service: HomeService = Requester.get().create(HomeService::class.java)
    override fun addOperate(
        number: String?,
        type: String?,
        longitude: String?,
        latitude: String?,
        checkName: String?,
        checkPhone: String?,
        oysterIdList: String?
    ): Observable<BaseBean> {

        return service.addOperate(
            number,
            type,
            longitude,
            latitude,
            checkName,
            checkPhone,
            oysterIdList,
            "下苗"
        )
    }

}