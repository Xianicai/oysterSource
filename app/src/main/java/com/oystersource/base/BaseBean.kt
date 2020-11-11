package com.oystersource.base

open class BaseBean {
    /**
     * code : 200
     * content :
     * message : 成功
     */
    var code = 0
    var message: String? = null
    open val isSuccess: Boolean
        get() = code == 200
}