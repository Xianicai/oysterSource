package com.oystersource.person.model

import com.oystersource.person.model.bean.UserBean
import com.oystersource.home.model.service.PersonService
import com.oystersource.utils.retrofit.Requester
import io.reactivex.Observable

interface IPersonModel {
    fun getUserDetail(userId: String): Observable<UserBean>
}

class PersonModel : IPersonModel {


    private var userService: PersonService = Requester.get().create(PersonService::class.java)
    override fun getUserDetail(userId: String): Observable<UserBean> {
        return userService.getUserDetail(userId)
    }

}