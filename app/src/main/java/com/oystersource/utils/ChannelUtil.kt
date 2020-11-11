package com.oystersource

import android.content.Context
import android.content.pm.PackageManager
import com.oystersource.base.BaseApplication

/**
 * Created by Administrator on 2018/6/1.
 */
object ChannelUtil {
    private const val CHANNEL_KEY = "cjt_channel_key"
    private const val CHANNEL_VERSION_KEY = "cjt_version"

    //应用宝渠道
    const val CHANNEL_KEY_YINGYONGBAO = "4"

    /**
     * 从包信息中获取版本号
     *
     * @param context
     * @return
     */
    fun getVersionCode(context: Context?): String {
        return try {
            val pi = BaseApplication.instance.packageManager
                .getPackageInfo(BaseApplication.instance.packageName, 0)
            pi.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            "未知版本号"
        }
    }

    @JvmStatic
    fun getChannelName(ctx: Context?): String? {
        if (ctx == null) {
            return null
        }
        var channelName: String? = null
        try {
            val packageManager = ctx.packageManager
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                val applicationInfo = packageManager.getApplicationInfo(
                    ctx.packageName,
                    PackageManager.GET_META_DATA
                )
                if (applicationInfo?.metaData != null) {
                    channelName = applicationInfo.metaData.getString("UMENG_CHANNEL")
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return channelName
    }
}