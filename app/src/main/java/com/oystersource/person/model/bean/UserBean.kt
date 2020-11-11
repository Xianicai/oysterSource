package com.oystersource.person.model.bean

import com.oystersource.base.BaseBean

class UserBean(var data: UserData) : BaseBean()
class UserData(
    var id: String = "",
    var name: String?,
    var password: String?,
    var phone: String?,
    var role: String?
)