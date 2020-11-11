package com.savegoldmaster.utils.view

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import com.oystersource.MainActivity
import com.oystersource.R
import com.oystersource.utils.SharedPreferencesHelper
import com.oystersource.utils.ToastUtil
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.UpgradeInfo
import com.tencent.bugly.beta.download.DownloadTask
import com.tencent.bugly.beta.ui.UILifecycleListener
import com.tencent.bugly.crashreport.CrashReport



class BuglyUtils {
    companion object {
        fun initBugly(context: Context, appId: String, debug: Boolean) {

            Beta.canShowUpgradeActs.add(MainActivity::class.java)
            Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog
            Beta.strUpgradeDialogInstallBtn = "立即更新"
            Beta.strUpgradeDialogCancelBtn = ""
            //延迟检测时间
            Beta.initDelay = 1 * 1000
            //升级检查周期设置
            Beta.upgradeCheckPeriod = 10 * 1000
            Beta.canShowUpgradeActs.add(MainActivity::class.java)

            val telephone =
                SharedPreferencesHelper(context, "UserBean").getSharedPreference("telephone", "").toString().trim()
            CrashReport.setUserId(telephone)
            addUpgradeDialogListener()
            Bugly.init(context, appId, debug)
        }

        /**
         * 设置升级对话框生命周期回调接口
         */
        fun addUpgradeDialogListener() {
            Beta.upgradeDialogLifecycleListener = object : UILifecycleListener<UpgradeInfo> {

                override fun onCreate(context: Context, view: View, upgradeInfo: UpgradeInfo) {
                    val mProgressBar = view.findViewById(R.id.mProgressBar) as ProgressBar
                    /*注册下载监听，监听下载事件*/
                    Beta.registerDownloadListener(object : com.tencent.bugly.beta.download.DownloadListener {
                        override fun onFailed(p0: DownloadTask?, p1: Int, p2: String?) {
                            ToastUtil.showMessage("下载失败")
                        }

                        override fun onReceive(p0: DownloadTask?) {

                        }

                        override fun onCompleted(p0: DownloadTask?) {
                            mProgressBar.progress = p0?.savedLength?.toInt()!!
                        }
                    })
                }

                override fun onResume(p0: Context?, p1: View?, p2: UpgradeInfo?) {
                }

                override fun onPause(p0: Context?, p1: View?, p2: UpgradeInfo?) {
                }

                override fun onStart(p0: Context?, p1: View?, p2: UpgradeInfo?) {
                }

                override fun onStop(p0: Context?, p1: View?, p2: UpgradeInfo?) {
                }

                override fun onDestroy(p0: Context?, p1: View?, p2: UpgradeInfo?) {
                }
            }


        }
    }

}