package com.oystersource

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.oystersource.base.BaseApplication.Companion.instance
import com.oystersource.person.view.LoginActivity
import com.oystersource.utils.SharedPreferencesHelper
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)
        QMUIStatusBarHelper.translucent(this)
        initView()
    }

    private fun initView() {
        var userId = ""
        val preferencesHelper = SharedPreferencesHelper(this, "LoginBean")
        userId = preferencesHelper.getSharedPreference("userId", userId).toString()
        if (!TextUtils.isEmpty(userId)) {
            MainActivity.start(this)
        } else {
            LoginActivity.start(this)
        }
        finish()
    }
}
