package com.oystersource.home.view

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.LocationSource
import com.amap.api.maps.LocationSource.OnLocationChangedListener
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.elvishew.xlog.XLog
import com.oystersource.R
import com.oystersource.home.view.adapter.LocationListAdapter
import com.oystersource.utils.OnResultCallback
import com.oystersource.utils.adapter.OnItemClickListener
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.activity_map.*


class MapActivity : AppCompatActivity(), PoiSearch.OnPoiSearchListener,
    AMap.OnMyLocationChangeListener, LocationSource, OnLocationChangedListener,
    AMapLocationListener {
    companion object {
        fun start(context: Activity?, requestCode: Int) {
            context?.startActivityForResult(Intent(context, MapActivity::class.java), requestCode)
        }
    }

    private var locationList: ArrayList<PoiItem> = ArrayList()
    private var locationListAdapter: LocationListAdapter? = null

    var mListener: OnLocationChangedListener? = null
    var mLocationClient: AMapLocationClient? = null
    var mLocationOption: AMapLocationClientOption? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        QMUIStatusBarHelper.translucent(this)
        initMap(savedInstanceState)
        initView()
    }


    private fun initView() {
        mActionBar.setBackOnListener {
            finish()
        }
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        locationListAdapter = LocationListAdapter(this, locationList)
        mRecyclerView.adapter = locationListAdapter
        locationListAdapter?.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent()
                intent.putExtra("PoiItem", locationList[position]) //将计算的值回传回去
                setResult(RESULT_OK, intent)
                finish()
            }

        })
        mImageDelete.setOnClickListener {
            mEdSearch.setText("")
            locationList.clear()
            locationListAdapter?.notifyDataSetChanged()
        }
        mEdSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (TextUtils.isEmpty(s.toString())) {
                    mImageDelete.visibility = View.GONE
                } else {
                    mImageDelete.visibility = View.VISIBLE
                }
                val query = PoiSearch.Query(s.toString(), "", "")
                query.pageSize = 10 // 设置每页最多返回多少条poiitem
                query.pageNum = 0 //设置查询页码
                val poiSearch = PoiSearch(this@MapActivity, query)
                poiSearch.setOnPoiSearchListener(this@MapActivity)
                poiSearch.searchPOIAsyn()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun initMap(savedInstanceState: Bundle?) {
        mMapView.onCreate(savedInstanceState) // 此方法必须重写
        // 显示地图
        val aMap = mMapView.map
        // 显示定位蓝点
        val myLocationStyle =
            MyLocationStyle() //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE)
        myLocationStyle.showMyLocation(true)
        aMap.myLocationStyle = myLocationStyle //设置定位蓝点的Style
        aMap.uiSettings.isMyLocationButtonEnabled = false // 设置默认定位按钮是否显示，非必需设置。
        aMap.isMyLocationEnabled = true // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setLocationSource(this)//通过aMap对象设置定位数据源的监听

    }

    override fun onDestroy() {
        super.onDestroy()
        // 销毁地图
        mMapView.onDestroy()
        mLocationClient?.onDestroy()
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

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        // 保存地图当前状态
        mMapView.onSaveInstanceState(outState)
    }


    override fun onPoiSearched(poiResult: PoiResult?, p1: Int) {
        locationList.clear()
        poiResult?.pois ?: return
        locationList.addAll(poiResult.pois)
        locationListAdapter?.notifyDataSetChanged()
    }

    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {}


    /**
     * 激活定位
     */
    override fun activate(listener: OnLocationChangedListener) {
        mListener = listener
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = AMapLocationClient(this)
            //初始化定位参数
            mLocationOption = AMapLocationClientOption()
            //设置定位回调监听
            mLocationClient!!.setLocationListener(this)
            //设置为高精度定位模式
            mLocationOption!!.locationMode = AMapLocationMode.Hight_Accuracy
            //设置定位参数
            mLocationClient!!.setLocationOption(mLocationOption)
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient!!.startLocation() //启动定位
        }
    }

    /**
     * 停止定位
     */
    override fun deactivate() {
        mListener = null
        if (mLocationClient != null) {
            mLocationClient!!.stopLocation()
            mLocationClient!!.onDestroy()
        }
        mLocationClient = null
    }

    override fun onMyLocationChange(p0: Location?) {}


    override fun onLocationChanged(amapLocation: AMapLocation?) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation.errorCode == 0) {
                mListener!!.onLocationChanged(amapLocation) // 显示系统小蓝点
            } else {
                val errText = "定位失败," + amapLocation.getErrorCode()
                    .toString() + ": " + amapLocation.getErrorInfo()
                XLog.e("AmapErr", errText)
            }
        }
    }

    override fun onLocationChanged(p0: Location?) {}
}



