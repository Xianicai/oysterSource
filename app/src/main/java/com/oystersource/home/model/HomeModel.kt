package com.oystersource.home.model

import com.oystersource.person.model.bean.UserBean
import io.reactivex.Observable

interface HomeModel {
    fun getRecycleGold(): Observable<UserBean>
}