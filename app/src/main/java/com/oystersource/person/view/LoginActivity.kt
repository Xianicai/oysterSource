package com.oystersource.person.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.oystersource.MainActivity
import com.oystersource.R
import com.oystersource.base.view.BaseMVPActivity
import com.oystersource.person.model.bean.LoginBean
import com.oystersource.person.presenter.LoginPresenterImpl
import com.oystersource.person.presenter.contract.LoginContract
import com.oystersource.utils.KeyboardUtils
import com.oystersource.utils.SharedPreferencesHelper
import com.oystersource.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseMVPActivity<LoginPresenterImpl>(), LoginContract.LoginView,
    View.OnClickListener {

    companion object {
        fun start(context: Context) {
            context.startActivity(
                Intent(context, LoginActivity::class.java)
            )
        }
    }

    private var loginPresenterImpl: LoginPresenterImpl? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }


    override fun initViews(savedInstanceState: Bundle?) {
        mTvLogin.setOnClickListener(this)
        mTvToRegister.setOnClickListener(this)
        //点击软键盘外部，收起软键盘
        LayoutParent.setOnClickListener {
            KeyboardUtils.hideKeyboard(LayoutParent)
        }

    }

    override fun createPresenter(): LoginPresenterImpl {
        loginPresenterImpl = LoginPresenterImpl()
        return loginPresenterImpl!!
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mTvLogin -> {
                when {
                    TextUtils.isEmpty(mEdPhoneNum.text) -> {
                        ToastUtil.showMessage("请输入手机号")
                    }
                    TextUtils.isEmpty(mEdPassword.text) -> {
                        ToastUtil.showMessage("请输入密码")
                    }
                    else -> {
                        loginPresenterImpl?.login(
                            mEdPhoneNum.text.toString(),
                            mEdPassword.text.toString()
                        )
                    }
                }
            }
            R.id.mTvToRegister -> {
                RegisterActivity.start(this)
            }
        }
    }


    override fun login(bean: LoginBean?) {
        ToastUtil.showMessage("登录成功")
        SharedPreferencesHelper(this, "LoginBean").apply {
            put("token", bean?.data?.token)
            put("userId", bean?.data?.userId)
        }
        MainActivity.start(this)
        finish()
    }
}