package com.oystersource.source.service

import com.oystersource.base.BaseBean
import com.oystersource.common.Urls
import com.oystersource.home.model.bean.ImageBean
import com.oystersource.home.model.bean.OysterBean
import com.oystersource.person.model.bean.UserBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*


interface SourceService {
    /**
     * 获取溯源信息
     *
     * @return
     */
    @GET(Urls.GET_SOURCE_INFO)
    fun getSourceInfo(
//        @Query("sourceId") sourceId: String
    ): Observable<BaseBean>

}