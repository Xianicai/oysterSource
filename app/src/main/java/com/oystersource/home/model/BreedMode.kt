package com.oystersource.home.model

import com.oystersource.base.BaseBean
import com.oystersource.home.model.bean.OysterBean
import com.oystersource.home.model.service.HomeService
import com.oystersource.utils.retrofit.Requester
import io.reactivex.Observable
import retrofit2.http.Field

interface IBreedMode{
    fun breedOperate(
        number: String?,
        label: String?,
        longitude: String?,
        latitude: String?,
        amount: String?,
        type: String?,
        imgPathList: String?,
        checkName: String?,
        checkPhone: String?
    ): Observable<BaseBean>
}

class BreedMode : IBreedMode {
    private var service: HomeService = Requester.get().create(HomeService::class.java)
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
    ): Observable<BaseBean> {
        return service.breedOperate(
            number,
            label,
            longitude,
            latitude,
            amount,
            type,
            imgPathList,
            checkName,
            checkPhone,
            "养殖"
        )
    }

}