package com.oystersource.utils.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import kotlinx.android.synthetic.main.activity_developer.*
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
import kotlin.system.exitProcess
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.oystersource.R
import com.oystersource.common.Urls
import com.oystersource.utils.SharedPreferencesHelper
import com.oystersource.utils.ToastUtil
import com.oystersource.utils.rxbus.EventConstant
import com.oystersource.utils.rxbus.RxBus
import com.oystersource.utils.rxbus.RxEvent


class DeveloperActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        fun start(context: Context) {
            context.startActivity(
                Intent(context, DeveloperActivity::class.java)
            )
        }
    }

    private var killProcess = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)
        setTheme(R.style.AppTheme)
        initView()
    }

    private fun initView() {
        val itemView = groupListView.createItemView("选择服务器").apply {
            accessoryType = QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        }
        QMUIGroupListView.newSection(this)
            .setTitle("常用开发工具：")
            .addItemView(itemView) { showEnvironment() }
            .addTo(groupListView)
    }

    override fun onClick(v: View?) {
    }

    private fun showEnvironment() {
        QMUIBottomSheet.BottomListSheetBuilder(this)
            .addItem("测试环境")
            .addItem("正式环境")
            .setOnSheetItemClickListener { dialog, itemView, position, tag ->
                var baseUrl = ""
                when (position) {
                    0 -> {
                        baseUrl = Urls.APP_TEST_BASE_URL
                    }
                    2 -> {
                        baseUrl = Urls.APP_FORMAL_BASE_URL
                    }
                }
                RxBus.default?.post(RxEvent(EventConstant.OUT_LOGIN, ""))
                SharedPreferencesHelper(this, "BaseUrl").apply {
                    put("BaseUrl", baseUrl)
                }
                killProcess = true
                finish()
            }.build()
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (killProcess) {
            ToastUtil.showMessage("即将关闭App")
            val handler = Handler()
            handler.postDelayed(
                {
                    android.os.Process.killProcess(android.os.Process.myPid())
                    exitProcess(0)
                }, 1000
            )
        }
    }

}
