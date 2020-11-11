package com.oystersource.home.model.bean

import android.os.Parcelable
import com.oystersource.base.BaseBean
import kotlinx.android.parcel.Parcelize

class OysterBean(var data: OysterData) : BaseBean()
@Parcelize
class OysterData(
    var amount: String = "",
    var id: String = "",
    var state: String = "",
    var type: String = "",
    var addTime: String = "",
    var imgList: List<imgList>? = null
): Parcelable
@Parcelize
class imgList (
    var img: String = "",
    var id: String = "",
    var oysterId: String = "",
): Parcelable