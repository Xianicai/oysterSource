package com.oystersource.person.model

import com.oystersource.base.BaseBean
import com.oystersource.home.model.service.PersonService
import com.oystersource.utils.retrofit.Requester
import io.reactivex.Observable

interface IRegisterMode {
    fun registerUser(
        name: String,
        password: String,
        phone: String,
        role: String
    ): Observable<BaseBean>
}

class RegisterMode : IRegisterMode {


    private var userService: PersonService = Requester.get().create(PersonService::class.java)


    override fun registerUser(
        name: String, password: String, phone: String, role: String
    ): Observable<BaseBean> {
        return userService.postRegistry(name, password, phone, role)
    }

}