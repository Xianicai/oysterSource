package com.oystersource.source.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oystersource.R
import com.oystersource.base.view.BaseMVPActivity
import com.oystersource.person.presenter.PersonPresenterImpl

class CodeActivity : BaseMVPActivity<PersonPresenterImpl>() {


    override fun getLayoutId(): Int {
       return R.layout.activity_code
    }

    override fun createPresenter(): PersonPresenterImpl {
        return PersonPresenterImpl()
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }
}