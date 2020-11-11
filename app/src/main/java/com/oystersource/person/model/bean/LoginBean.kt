package com.oystersource.person.model.bean

import com.oystersource.base.BaseBean

class LoginBean(var data: LoginData) : BaseBean()

class LoginData(var userId: String ="", var token: String?)