package com.oystersource.home.model.service

import com.oystersource.base.BaseBean
import com.oystersource.common.Urls
import com.oystersource.person.model.bean.UserBean
import com.oystersource.person.model.bean.LoginBean
import io.reactivex.Observable
import retrofit2.http.*


interface PersonService {

    /**
     * 用户注册
     */
    @FormUrlEncoded
    @POST(Urls.POST_USER_REGISTRY)
    fun postRegistry(
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("phone") phone: String,
        @Field("role") role: String
    ): Observable<BaseBean>

    /**
     * 用户登录
     */
    @FormUrlEncoded
    @POST(Urls.ACCOUNT_LOGIN)
    fun login(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Observable<LoginBean>

    /**
     * 获取个人信息
     *
     * @return
     */
    @GET(Urls.GET_USER_DETAIL)
    fun getUserDetail(@Query("userId") userId: String): Observable<UserBean>


}


