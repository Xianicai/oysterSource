package com.oystersource.source.model

import com.oystersource.base.BaseBean
import com.oystersource.person.model.bean.UserBean
import com.oystersource.home.model.service.PersonService
import com.oystersource.person.model.IPersonModel
import com.oystersource.source.service.SourceService
import com.oystersource.utils.retrofit.Requester
import io.reactivex.Observable

interface ISourceMode {
    fun getSourceInfo(): Observable<BaseBean>
}

class SourceMode : ISourceMode {
    private var service: SourceService = Requester.get().create(SourceService::class.java)
    override fun getSourceInfo(): Observable<BaseBean> {
        return service.getSourceInfo()
    }

}