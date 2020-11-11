package com.oystersource.base

import android.app.Application
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import android.content.Context
import androidx.multidex.MultiDex
import com.oystersource.BuildConfig
import com.savegoldmaster.utils.view.BuglyUtils


/**
 * ZY:
 * Created by zhanglibin on 2018/9/2.
 */
class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLog()
        initBugly()
        initQMUI()

    }

    private fun initQMUI() {

//        QMUISwipeBackActivityManager.init(this)
    }



    private fun initBugly() {
        BuglyUtils.initBugly(instance, Constant.BUGLY_APPID, BuildConfig.DEBUG)
    }

    private fun initLog() {
        XLog.init(if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE)
    }





    /**
     * MultiDex引入
     * */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
       MultiDex.install(this)
    }
}