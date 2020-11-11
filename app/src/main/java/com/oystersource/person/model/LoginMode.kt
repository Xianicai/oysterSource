package com.oystersource.person.model

import com.oystersource.home.model.service.PersonService
import com.oystersource.person.model.bean.LoginBean
import com.oystersource.utils.retrofit.Requester
import io.reactivex.Observable

interface ILoginMode {
    fun login(phone: String, password: String): Observable<LoginBean>
}

class LoginMode : ILoginMode {


    private var userService: PersonService = Requester.get().create(PersonService::class.java)


    override fun login(phone: String, password: String): Observable<LoginBean> {
        return userService.login(phone, password)
    }

}