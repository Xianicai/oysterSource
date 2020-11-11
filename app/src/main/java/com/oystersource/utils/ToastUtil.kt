package com.oystersource.utils

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.oystersource.base.BaseApplication

/**
 * ZY:统一的Toast类
 * Created by zhanglibin on 2016/9/8.
 */
object ToastUtil {

    private val handler = Handler(Looper.getMainLooper())

    private var toast: Toast? = null

    private val synObj = Any()

    fun showMessage(msg: String?) {
        msg ?: return
        showMessage(msg, Toast.LENGTH_SHORT)
    }

    fun showMessage(msg: CharSequence?, len: Int) {
        if (msg == null || msg == "") {
            return
        }
        handler.post {
            //加上同步是为了每个toast只要有机会显示出来
            synchronized(synObj) {
                if (toast != null) {
                    toast!!.setText(msg)
                    toast!!.duration = len
                } else {
                    toast = Toast.makeText(BaseApplication.instance, msg, len)
                }
                toast!!.show()
            }
        }
    }

    @JvmOverloads
    fun showMessage(msg: Int, len: Int = Toast.LENGTH_SHORT) {
        handler.post {
            synchronized(synObj) {
                if (toast != null) {
                    toast!!.setText(msg)
                    toast!!.duration = len
                } else {
                    toast = Toast.makeText(BaseApplication.instance?.applicationContext, msg, len)
                }
                toast!!.show()
            }
        }
    }

    fun cancel() {
        if (toast != null) {
            toast!!.cancel()
        }
    }
}

