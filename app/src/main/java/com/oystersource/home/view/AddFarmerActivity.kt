package com.oystersource.home.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.core.PoiItem
import com.oystersource.R
import com.oystersource.base.BaseBean
import com.oystersource.base.view.BaseMVPActivity
import com.oystersource.home.presenter.AddFarmerPresenterImpl
import com.oystersource.home.presenter.Contract.AddFarmerContract
import com.oystersource.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_add_farmer.*


class AddFarmerActivity : BaseMVPActivity<AddFarmerPresenterImpl>(),
    AddFarmerContract.AddFarmerView,
    View.OnClickListener {
    companion object {
        fun start(context: Context?) {
            context?.startActivity(
                Intent(context, AddFarmerActivity::class.java)
            )
        }
    }

    private val REQUEST_CODE_MAP = 3000
    var presenterImpl: AddFarmerPresenterImpl? = null
    var poiItem: PoiItem? = null
    var aMap: AMap? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_add_farmer
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        // 保存地图当前状态
        mMapView.onSaveInstanceState(outState)
    }


    override fun createPresenter(): AddFarmerPresenterImpl {
        presenterImpl = AddFarmerPresenterImpl()
        return presenterImpl!!
    }

    override fun initViews(savedInstanceState: Bundle?) {
        initMap(savedInstanceState)
        mActionBar.setBackOnListener {
            onBackPressed()
        }
        mTvAdd.setOnClickListener(this)
        mLayoutFarmAd.setOnClickListener(this)
        mLayoutFarmAd.getEdView()?.isEnabled = false
        mLayoutPhone.getEdView()?.inputType = InputType.TYPE_CLASS_NUMBER
        mLayoutCount.getEdView()?.inputType = InputType.TYPE_CLASS_NUMBER
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
                    TextUtils.isEmpty(mLayoutName.getEdString()) -> {
                        ToastUtil.showMessage("请输入养殖户姓名")
                    }
                    TextUtils.isEmpty(mLayoutPhone.getEdString()) -> {
                        ToastUtil.showMessage("请输入联系电话")
                    }
                    TextUtils.isEmpty(mLayoutAd.getEdString()) -> {
                        ToastUtil.showMessage("请输入常用地址")
                    }
                    TextUtils.isEmpty(mLayoutFarmYear.getEdString()) -> {
                        ToastUtil.showMessage("请输入承包养殖年限")
                    }
                    TextUtils.isEmpty(mLayoutCount.getEdString()) -> {
                        ToastUtil.showMessage("请输入水泥串浮阀数量")
                    }
                    TextUtils.isEmpty(mLayoutFarmNum.getEdString()) -> {
                        ToastUtil.showMessage("请输入养殖编号")
                    }
                    TextUtils.isEmpty(mLayoutFarmArea.getEdString()) -> {
                        ToastUtil.showMessage("请输入养殖区域面积")
                    }

                    TextUtils.isEmpty(mLayoutFarmOutput.getEdString()) -> {
                        ToastUtil.showMessage("请输入养殖预计产量")
                    }
                    poiItem == null -> {
                        ToastUtil.showMessage("请输入选择养殖位置")
                    }
                    else -> {
                        presenterImpl?.addFarmer(
                            mLayoutName.getEdString(),
                            mLayoutPhone.getEdString(),
                            mLayoutAd.getEdString(),
                            mLayoutFarmYear.getEdString(),
                            Integer.valueOf(mLayoutCount.getEdString().toString()),
                            mLayoutFarmNum.getEdString(),
                            mLayoutFarmArea.getEdString(),
                            mLayoutFarmOutput.getEdString(),
                            poiItem?.latLonPoint?.longitude.toString(),
                            poiItem?.latLonPoint?.latitude.toString(),
                        )
                    }
                }
            }
            R.id.mLayoutFarmAd -> {
                MapActivity.start(this, REQUEST_CODE_MAP)
            }

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_CODE_MAP -> {
                poiItem = data?.getParcelableExtra<PoiItem>("PoiItem")
                mLayoutFarmAd.getEdView()?.setText(poiItem?.title)
                val latLng = LatLng(
                    poiItem!!.latLonPoint!!.latitude,
                    poiItem!!.latLonPoint!!.longitude
                )
                val marker = aMap!!.addMarker(
                    MarkerOptions().position(latLng).title(poiItem!!.title).snippet(poiItem?.adName + poiItem?.snippet)
                )
                marker.showInfoWindow()
            }

        }
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

    override fun addFarmer(bean: BaseBean?) {
    }

}