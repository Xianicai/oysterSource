package com.oystersource.home.model

import com.oystersource.base.BaseBean
import com.oystersource.home.model.bean.OysterBean
import com.oystersource.home.model.service.HomeService
import com.oystersource.utils.retrofit.Requester
import io.reactivex.Observable
import retrofit2.http.Field

interface IAddFarmerMode {
    fun addFarmer(
        name: String?,
        phone: String?,
        address: String?,
        breedYear: String?,
        raftCount: Int?, //阀数
        checkName: String?, //编号
        breedArea: String?,//面积
        breadProduct: String?, //产量
        longitude: String?,
        latitude: String?,
    ): Observable<BaseBean>
}

class AddFarmerMode : IAddFarmerMode {
    private var service: HomeService = Requester.get().create(HomeService::class.java)
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
    ): Observable<BaseBean> {

        return service.addFarmer(
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
    }

}