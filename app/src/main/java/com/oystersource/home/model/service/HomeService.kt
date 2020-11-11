package com.oystersource.home.model.service

import com.oystersource.base.BaseBean
import com.oystersource.common.Urls
import com.oystersource.home.model.bean.ImageBean
import com.oystersource.home.model.bean.OysterBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*


interface HomeService {

    /**
     * 添加生蚝
     *
     * @return
     */
    @FormUrlEncoded
    @POST(Urls.POST_ADD_ADDOYSTER)
    fun addOyster(
        @Field("amount") amount: String,
        @Field("type") type: String,
        @Field("state") state: String,
        @Field("imgPathList") imgPathList: String?
    ): Observable<OysterBean>

    /**
     * 下苗生蚝
     *
     * @return
     */
    @FormUrlEncoded
    @POST(Urls.POST_ADD_OPERATE)
    fun addOperate(
        @Field("number") number: String?,
        @Field("label") label: String?,
        @Field("longitude") longitude: String?,
        @Field("latitude") latitude: String?,
        @Field("checkName") checkName: String?,
        @Field("checkPhone") checkPhone: String?,
        @Field("oysterIdList") oysterIdList: String?,
        @Field("state") state: String?
    ): Observable<BaseBean>


    /**
     * 养殖
     *
     * @return
     */
    @FormUrlEncoded
    @POST(Urls.POST_ADD_OPERATE)
    fun breedOperate(
        @Field("number") number: String?,
        @Field("label") label: String?,
        @Field("longitude") longitude: String?,
        @Field("latitude") latitude: String?,
        @Field("amount") amount: String?,
        @Field("type") type: String?,
        @Field("imgPathList") imgPathList: String?,
        @Field("checkName") checkName: String?,
        @Field("checkPhone") checkPhone: String?,
        @Field("state") state: String?
    ): Observable<BaseBean>
    /**
     * 增加确权人
     *
     * @return
     */
    @FormUrlEncoded
    @POST(Urls.POST_ADD_FARMER)
    fun addFarmer(
        @Field("name") name: String?,
        @Field("phone") phone: String?,
        @Field("address") address: String?,
        @Field("breedYear") breedYear: String?,
        @Field("raftCount") raftCount: Int?, //阀数
        @Field("farmNumber") farmNumber: String?, //编号
        @Field("breedArea") breedArea: String?,//面积
        @Field("breadProduct") breadProduct: String?, //产量
        @Field("longitude") longitude: String?,
        @Field("latitude") latitude: String?,
    ): Observable<BaseBean>

    /**
     *上传图片
     *
     * @return
     */
    @Multipart
    @POST(Urls.POST_UPLOAD_FILE)
    fun uploadFile(@Part imgs: List<MultipartBody.Part>?): Observable<ImageBean>
}