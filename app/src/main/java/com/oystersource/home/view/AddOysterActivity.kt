package com.oystersource.home.view

import android.Manifest.permission
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.oystersource.R
import com.oystersource.base.view.BaseMVPActivity
import com.oystersource.home.model.bean.OysterBean
import com.oystersource.home.presenter.AddOysterPresenterImpl
import com.oystersource.home.presenter.Contract.AddOysterContract
import com.oystersource.home.view.adapter.ImageListAdapter
import com.oystersource.utils.FileUtil
import com.oystersource.utils.OnResultCallback
import com.oystersource.utils.ToastUtil
import com.oystersource.utils.view.AndroidBug5497Workaround
import com.tbruyelle.rxpermissions3.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.android.synthetic.main.activity_add_oyster_info.*


class AddOysterActivity : BaseMVPActivity<AddOysterPresenterImpl>(),
    AddOysterContract.AddOysterView,
    View.OnClickListener {

    companion object {
        fun start(context: Activity?, requestCode: Int) {
            context?.startActivityForResult(
                Intent(context, AddOysterActivity::class.java),
                requestCode
            )
        }
    }


    var imageListAdapter: ImageListAdapter? = null
    var addOysterInfoPresenterImpl: AddOysterPresenterImpl? = null
    private val REQUEST_CODE_IMAGE_CHOOSE = 1000
    private var mSelected = ArrayList<Uri>()
    override fun getLayoutId(): Int {
        return R.layout.activity_add_oyster_info
    }

    override fun createPresenter(): AddOysterPresenterImpl {
        addOysterInfoPresenterImpl = AddOysterPresenterImpl()
        return addOysterInfoPresenterImpl!!
    }

    override fun initViews(savedInstanceState: Bundle?) {
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content))

        mTvAdd.setOnClickListener(this)
        mAddImage.setOnClickListener(this)
        mActionBar.setBackOnListener {
            onBackPressed()
        }
        mLayoutOysterNum.getEdView()?.inputType = InputType.TYPE_CLASS_NUMBER
        mRecyclerView.layoutManager = GridLayoutManager(this, 4)
        imageListAdapter = ImageListAdapter(this, mSelected)
        mRecyclerView.adapter = imageListAdapter
        imageListAdapter?.setDeleteImageLister(OnResultCallback {
            showAddImage()
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

   override fun addOyster(bean: OysterBean?) {
        val intent = Intent()
        intent.putExtra("OysterData", bean?.data)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mTvAdd -> {
                when {
                    TextUtils.isEmpty(mLayoutOysterNum.getEdString()) -> {
                        ToastUtil.showMessage("请输入生蚝数量")
                    }
                    TextUtils.isEmpty(mTvType.getEdString()) -> {
                        ToastUtil.showMessage("请输入生蚝品种")
                    }
                    TextUtils.isEmpty(mTvState.getEdString()) -> {
                        ToastUtil.showMessage("请输入生蚝健康状况")
                    }
                    mSelected.size < 1 -> {
                        ToastUtil.showMessage("请选择图片")
                    }
                    else -> {
                        FileUtil.uploadImages(FileUtil.uri2File(this, mSelected)) {
                            //上传成功的回调
                            addOysterInfoPresenterImpl?.addOyster(
                                mLayoutOysterNum.getEdString(),
                                mTvType.getEdString(),
                                mTvState.getEdString(),
                                it.data.imgUrls
                            )
                        }
                    }
                }
            }
            R.id.mAddImage -> {
                checkPermission()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE_CHOOSE && resultCode == RESULT_OK) {
            //图片路径 同样视频地址也是这个 根据requestCode
            mSelected.addAll(Matisse.obtainResult(data))
            imageListAdapter?.notifyDataSetChanged()
            showAddImage()
        }
    }

    private fun showAddImage() {
        if (mSelected.size > 3) {
            mAddImage.visibility = View.GONE
        } else {
            mAddImage.visibility = View.VISIBLE
        }
        mAddImage.requestLayout()
    }

    private fun checkPermission() {
        val permissions = RxPermissions(this)
        permissions.setLogging(true)
        permissions.request(
            permission.CAMERA,
            permission.WRITE_EXTERNAL_STORAGE,
            permission.READ_EXTERNAL_STORAGE
        ).subscribe(Consumer<Boolean> { aBoolean ->
            if (aBoolean == true) {
                //申请的权限全部允许
                selectImage() // 设置作为标记的请求码
            } else {
                //只要有一个权限被拒绝，就会执行
                ToastUtil.showMessage("未授权权限，改功能不能使用")
            }
        })
    }

    private fun selectImage() {
        Matisse.from(this)
            .choose(MimeType.ofImage(), false) // 选择 mime 的类型
            .countable(true)
            .capture(true)  // 使用相机，和 captureStrategy 一起使用
            .captureStrategy(CaptureStrategy(true, "com.jsf.piccompresstest"))
            .maxSelectable(4 - mSelected.size) // 图片选择的最多数量
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .thumbnailScale(0.85f) // 缩略图的比例
            .imageEngine(GlideEngine()) // 使用的图片加载引擎
            .forResult(REQUEST_CODE_IMAGE_CHOOSE)
    }
}