package com.oystersource.home.model.bean

import com.oystersource.base.BaseBean

class ImageBean(var data: ImageData) : BaseBean()
class ImageData(
    var imgUrls: List<String> ? =  null
)