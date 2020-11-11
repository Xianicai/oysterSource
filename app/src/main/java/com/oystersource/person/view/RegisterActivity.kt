package com.oystersource.person.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.oystersource.R
import com.oystersource.base.BaseBean
import com.oystersource.base.view.BaseMVPActivity
import com.oystersource.person.presenter.RegisterPresenterImpl
import com.oystersource.person.presenter.contract.RegisterContract
import com.oystersource.utils.KeyboardUtils
import com.oystersource.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.mEdPassword
import kotlinx.android.synthetic.main.activity_register.mEdPhoneNum

class RegisterActivity : BaseMVPActivity<RegisterPresenterImpl>(), RegisterContract.RegisterView,
    View.OnClickListener {

    companion object {
        fun start(context: Context) {
            context.startActivity(
                Intent(context, RegisterActivity::class.java)
            )
        }
    }

    private var registerPresenterImpl: RegisterPresenterImpl? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }


    override fun initViews(savedInstanceState: Bundle?) {
        mTvRegister.setOnClickListener(this)
        //点击软键盘外部，收起软键盘
        LayoutParent.setOnClickListener {
            KeyboardUtils.hideKeyboard(LayoutParent)
        }
    }

    override fun createPresenter(): RegisterPresenterImpl {
        registerPresenterImpl = RegisterPresenterImpl()
        return registerPresenterImpl!!
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mTvRegister -> {
                if (TextUtils.isEmpty(mEdPhoneNum.text)) {
                    ToastUtil.showMessage("请输入手机号")
                } else if (TextUtils.isEmpty(mEdPassword.text)) {
                    ToastUtil.showMessage("请输入密码")
                } else if (TextUtils.isEmpty(mEdConfirmPassword.text)) {
                    ToastUtil.showMessage("请确认密码")
                } else if (TextUtils.isEmpty(mEdName.text)) {
                    ToastUtil.showMessage("请输入姓名昵称")
                } else if (TextUtils.isEmpty(mEdType.text)) {
                    ToastUtil.showMessage("请选择巡检身份")
                } else if (!TextUtils.equals(mEdPassword.text, mEdConfirmPassword.text)) {
                    ToastUtil.showMessage("输入密码和确认密码不一致，请重新输入")
                } else if (mEdPassword.text.toString().length < 6) {
                    ToastUtil.showMessage("请输入6位或者6位以上密码")
                } else {
                    registerPresenterImpl?.registerUser(
                        mEdName.text.toString(),
                        mEdPassword.text.toString(),
                        mEdPhoneNum.text.toString(),
                        "法师"
                    )
                }
            }
        }
    }


    override fun registerUser(userBean: BaseBean?) {
        ToastUtil.showMessage("注册成功,请登录")
        LoginActivity.start(this)
        finish()
    }
//    private fun showTips() {
//        ConfirmDialog(this)
//            .setTitle("提示")
//            .setMessage("是否找回密码？")
//            .setTwoButtonListener("取消",
//                { dialog, v ->
//                    dialog.dismiss()
//                }, "前往", { dialog, _ ->
//
//                    dialog.dismiss()
//                }).show()
//    }

}