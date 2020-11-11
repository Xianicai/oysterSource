package com.oystersource.utils

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.amap.api.mapcore.util.it
import com.elvishew.xlog.XLog
import com.oystersource.home.model.bean.ImageBean
import com.oystersource.home.model.service.HomeService
import com.oystersource.utils.retrofit.Requester
import com.oystersource.utils.retrofit.RespondObserver
import com.oystersource.utils.retrofit.ThreadTransformer
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

object FileUtil {
    fun getFilesAllName(path: String?): List<String>? {
        val file = File(path)
        val files = file.listFiles()
        if (files == null) {
            XLog.i("error", "空目录")
            return null
        }
        val s: MutableList<String> = ArrayList()
        for (i in files.indices) {
            s.add(files[i].absolutePath)
        }
        return s
    }

    /**
     * uri转换为file文件
     * 返回值为file类型
     * @param uri
     * @return
     */
    fun uri2File(activity: Activity, uris: List<Uri>?): List<File>? {
        var files = ArrayList<File>()
        uris?.forEach {
            val imagePath: String?
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val actualimagecursor: Cursor = activity.managedQuery(
                it, proj, null,
                null, null
            )
            imagePath = run {
                val actualImageColumnIndex: Int = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                actualimagecursor.moveToFirst()
                actualimagecursor
                    .getString(actualImageColumnIndex)
            }
            files.add(File(imagePath))
        }
        return files
    }

    fun uploadImages(file: List<File>?, callback: OnResultCallback<ImageBean>?) {
        file ?: return
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            file.forEach {
                val requestBody =
                    RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), it)
                builder.addFormDataPart("files", it.name, requestBody) //files 文件上传参数
            }
            val parts = builder.build().parts()

            Requester.get().create(HomeService::class.java)
                .uploadFile(parts)
                .compose(ThreadTransformer())
                .subscribe(object : RespondObserver<ImageBean>() {
                    override fun onSuccess(result: ImageBean?) {
                        super.onSuccess(result)
                        callback?.call(result)
                    }
                })

        }
    }
}