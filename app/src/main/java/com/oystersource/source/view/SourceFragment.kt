package com.oystersource.source.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.view.View
import com.oystersource.R
import com.oystersource.base.view.BaseMVPFragment
import com.oystersource.person.presenter.PersonPresenterImpl
import com.oystersource.utils.ToastUtil
import com.oystersource.utils.com.google.zxing.activity.CaptureActivity
import com.tbruyelle.rxpermissions3.RxPermissions
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.android.synthetic.main.fragment_source.*


class SourceFragment : BaseMVPFragment<PersonPresenterImpl>() {
    companion object {
        fun newInstance(): SourceFragment {
            return SourceFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_source
    }


    override fun createPresenter(): PersonPresenterImpl {
        return PersonPresenterImpl()

    }

    override fun initView(view: View?) {
        mLayoutQR.setOnClickListener {
            checkPermission()
        }
        mLayoutConfirm.setOnClickListener {
            ToastUtil.showMessage("暂时未开发")
//            SourceInfoActivity.start(activity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CaptureActivity.REQUEST_CODE_QR_SCAN && resultCode == Activity.RESULT_OK) {
            val string = data?.extras?.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
            ToastUtil.showMessage(string)
        }
    }
    private fun checkPermission() {
        val permissions = RxPermissions(this)
        permissions.setLogging(true)
        permissions.request(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ).subscribe { aBoolean ->
            if (aBoolean == true) {
                //申请的权限全部允许
                val mIntent = Intent(activity, CaptureActivity::class.java)
                startActivityForResult(mIntent, CaptureActivity.REQUEST_CODE_QR_SCAN)
            } else {
                //只要有一个权限被拒绝，就会执行
                ToastUtil.showMessage("手机权限未授权，该功能无法使用")
            }
        }
    }
}