package com.oystersource.home.model

import com.oystersource.home.model.bean.OysterBean
import com.oystersource.home.model.service.HomeService
import com.oystersource.utils.retrofit.Requester
import io.reactivex.Observable

interface IAddOysterMode {
    fun addOyster(
        amount: String,
        type: String,
        state: String,
        imgPathList: List<String>
    ): Observable<OysterBean>
}

class AddOysterMode : IAddOysterMode {
    private var service: HomeService = Requester.get().create(HomeService::class.java)
    override fun addOyster(
        amount: String,
        type: String,
        state: String,
        imgPathList: List<String>
    ): Observable<OysterBean> {
        var imgPath = ""
        for (index in imgPathList.indices) {
            imgPath = if (index < imgPathList.size - 1) {
                imgPath + imgPathList[index] + ","
            } else {
                imgPath + imgPathList[index]
            }
        }
        return service.addOyster(amount, type, state, imgPath)
    }

}