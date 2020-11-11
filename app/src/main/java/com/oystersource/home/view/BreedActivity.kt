package com.oystersource.home.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.core.PoiItem
import com.oystersource.R
import com.oystersource.base.BaseBean
import com.oystersource.base.view.BaseMVPActivity
import com.oystersource.home.presenter.AddFarmerPresenterImpl
import com.oystersource.home.presenter.BreedPresenterImpl
import com.oystersource.home.presenter.Contract.AddFarmerContract
import com.oystersource.home.presenter.Contract.BreedContract
import com.oystersource.home.view.adapter.ImageListAdapter
import com.oystersource.utils.*
import com.oystersource.utils.view.AndroidBug5497Workaround
import com.tbruyelle.rxpermissions3.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.android.synthetic.main.activity_add_oyster_info.*
import kotlinx.android.synthetic.main.activity_breed.*
import kotlinx.android.synthetic.main.activity_breed.mActionBar
import kotlinx.android.synthetic.main.activity_breed.mAddImage
import kotlinx.android.synthetic.main.activity_breed.mLayoutLabel
import kotlinx.android.synthetic.main.activity_breed.mLayoutMap
import kotlinx.android.synthetic.main.activity_breed.mLayoutName
import kotlinx.android.synthetic.main.activity_breed.mLayoutNum
import kotlinx.android.synthetic.main.activity_breed.mLayoutOysterNum
import kotlinx.android.synthetic.main.activity_breed.mLayoutPhone
import kotlinx.android.synthetic.main.activity_breed.mMapView
import kotlinx.android.synthetic.main.activity_breed.mRecyclerView
import kotlinx.android.synthetic.main.activity_breed.mTvAdd
import kotlinx.android.synthetic.main.activity_breed.mTvState


class BreedActivity : BaseMVPActivity<BreedPresenterImpl>(),
    BreedContract.BreedView,
    View.OnClickListener {
    companion object {
        fun start(context: Context?) {
            context?.startActivity(
                Intent(context, BreedActivity::class.java)
            )
        }
    }

    private val REQUEST_CODE_MAP = 4000
    private val REQUEST_CODE_IMAGE_CHOOSE = 4001
    private var mSelected = ArrayList<Uri>()
    var imageListAdapter: ImageListAdapter? = null
    var presenterImpl: BreedPresenterImpl? = null
    var poiItem: PoiItem? = null
    var aMap: AMap? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_breed
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        // 保存地图当前状态
        mMapView.onSaveInstanceState(outState)
    }


    override fun createPresenter(): BreedPresenterImpl {
        presenterImpl = BreedPresenterImpl()
        return presenterImpl!!
    }

    override fun initViews(savedInstanceState: Bundle?) {
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content))
        initMap(savedInstanceState)
        mActionBar.setBackOnListener {
            onBackPressed()
        }
        mLayoutNum.getEdView()?.isEnabled = false
        mLayoutNum.getEdView()?.setText(UUIDUtils.getUUID())
        mLayoutTime.getEdView()?.setText(TimeUtil.getCurrentDate())
        mTvAdd.setOnClickListener(this)
        mLayoutMap.setOnClickListener(this)
        mAddImage.setOnClickListener(this)
        mLayoutMap.getEdView()?.isEnabled = false
        mLayoutPhone.getEdView()?.inputType = InputType.TYPE_CLASS_NUMBER
        mRecyclerView.layoutManager = GridLayoutManager(this, 4)
        imageListAdapter = ImageListAdapter(this, mSelected)
        mRecyclerView.adapter = imageListAdapter
        imageListAdapter?.setDeleteImageLister(OnResultCallback {
            showAddImage()
        })
    }

    private fun initMap(savedInstanceState: Bundle?) {
        mMapView.onCreate(savedInstanceState) // 此方法必须重写
        // 显示地图
        aMap = mMapView.map
        // 显示定位蓝点
        val myLocationStyle =
            MyLocationStyle() //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE)
        myLocationStyle.showMyLocation(true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mTvAdd -> {
                when {
                    TextUtils.isEmpty(mLayoutLabel.getEdString()) -> {
                        ToastUtil.showMessage("请输入标签号")
                    }
                    TextUtils.isEmpty(mLayoutOysterNum.getEdString()) -> {
                        ToastUtil.showMessage("请输入生蚝只数")
                    }
                    TextUtils.isEmpty(mTvState.getEdString()) -> {
                        ToastUtil.showMessage("请输入生长趋势")
                    }
                    TextUtils.isEmpty(mLayoutName.getEdString()) -> {
                        ToastUtil.showMessage("请输入巡检人姓名")
                    }
                    TextUtils.isEmpty(mLayoutPhone.getEdString()) -> {
                        ToastUtil.showMessage("请输入联系电话")
                    }
                    poiItem == null -> {
                        ToastUtil.showMessage("请输入选择养殖位置")
                    }
                    mSelected.size < 1 -> {
                        ToastUtil.showMessage("请选择图片")
                    }
                    else -> {
                        FileUtil.uploadImages(FileUtil.uri2File(this, mSelected)) {
                            it.data.imgUrls ?: return@uploadImages
                            var imgPath = ""
                            for (index in it.data.imgUrls!!.indices) {
                                imgPath = if (index < it.data.imgUrls!!.size - 1) {
                                    imgPath + it.data.imgUrls!![index] + ","
                                } else {
                                    imgPath + it.data.imgUrls!![index]
                                }
                            }

                            //上传成功的回调
                            presenterImpl?.breedOperate(
                                mLayoutNum.getEdString(),
                                mLayoutLabel.getEdString(),
                                poiItem?.latLonPoint?.longitude.toString(),
                                poiItem?.latLonPoint?.latitude.toString(),
                                mLayoutOysterNum.getEdString(),
                                mTvState.getEdString(),
                                imgPath,
                                mLayoutName.getEdString(),
                                mLayoutPhone.getEdString(),
                            )
                        }
                    }
                }
            }
            R.id.mLayoutMap -> {
                MapActivity.start(this, REQUEST_CODE_MAP)
            }
            R.id.mAddImage -> {
                checkPermission()
            }

        }
    }

    private fun checkPermission() {
        val permissions = RxPermissions(this)
        permissions.setLogging(true)
        permissions.request(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_CODE_MAP -> {
                poiItem = data?.getParcelableExtra<PoiItem>("PoiItem")
                mLayoutMap.getEdView()?.setText(poiItem?.title)
                val latLng = LatLng(
                    poiItem!!.latLonPoint!!.latitude,
                    poiItem!!.latLonPoint!!.longitude
                )
                val marker = aMap!!.addMarker(
                    MarkerOptions().position(latLng).title(poiItem!!.title)
                        .snippet(poiItem?.adName + poiItem?.snippet)
                )
                marker.showInfoWindow()
            }
            REQUEST_CODE_IMAGE_CHOOSE -> {
                //图片路径 同样视频地址也是这个 根据requestCode
                mSelected.addAll(Matisse.obtainResult(data))
                imageListAdapter?.notifyDataSetChanged()
                showAddImage()
            }
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

    override fun onDestroy() {
        super.onDestroy()
        // 销毁地图
        mMapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        // 重新绘制加载地图
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        // 暂停地图的绘制
        mMapView.onPause()
    }

    override fun breedOperate(bean: BaseBean?) {
        ToastUtil.showMessage("养殖成功")
        finish()
    }
}