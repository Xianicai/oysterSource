package com.oystersource.home.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.core.PoiItem
import com.oystersource.R
import com.oystersource.base.BaseBean
import com.oystersource.base.view.BaseMVPActivity
import com.oystersource.home.model.bean.OysterBean
import com.oystersource.home.model.bean.OysterData
import com.oystersource.home.presenter.AddSeedPresenterImpl
import com.oystersource.home.presenter.Contract.AddSeedContract
import com.oystersource.home.view.adapter.ImageListAdapter
import com.oystersource.home.view.adapter.OysterListAdapter
import com.oystersource.utils.TimeUtil
import com.oystersource.utils.ToastUtil
import com.oystersource.utils.UUIDUtils
import com.oystersource.utils.adapter.OnItemClickListener
import com.oystersource.utils.view.AndroidBug5497Workaround
import kotlinx.android.synthetic.main.activity_add_seed.*


class AddSeedActivity : BaseMVPActivity<AddSeedPresenterImpl>(),
    AddSeedContract.AddSeedView,
    View.OnClickListener {
    companion object {
        fun start(context: Context?) {
            context?.startActivity(
                Intent(context, AddSeedActivity::class.java)
            )
        }
    }

    private val REQUEST_CODE_MAP = 2000
    private val REQUEST_CODE_OYSTER = 2001
    var imageListAdapter: ImageListAdapter? = null
    var presenterImpl: AddSeedPresenterImpl? = null
    var poiItem: PoiItem? = null
    var adapter: OysterListAdapter? = null
    var aMap: AMap? = null
    private var oysterDataList: ArrayList<OysterData> = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.activity_add_seed
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        // 保存地图当前状态
        mMapView.onSaveInstanceState(outState)
    }


    override fun createPresenter(): AddSeedPresenterImpl {
        presenterImpl = AddSeedPresenterImpl()
        return presenterImpl!!
    }

    override fun initViews(savedInstanceState: Bundle?) {
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content))
        initMap(savedInstanceState)
        mActionBar.setBackOnListener {
            onBackPressed()
        }
        mTvAdd.setOnClickListener(this)
        mTvAddOyster.setOnClickListener(this)
        mLayoutMap.setOnClickListener(this)
        mLayoutNum.getEdView()?.setText(UUIDUtils.getUUID())
        mLayoutNum.getEdView()?.isEnabled = false

        mLayoutMap.getEdView()?.isEnabled = false
        mLayoutMap.getEdView()?.isFocusable = false
        mLayoutMap.getEdView()?.isFocusableInTouchMode = false
        mLayoutMap.getEdView()?.isClickable = false
        mLayoutMap.getEdView()?.clearFocus()


        mLayoutPhone.getEdView()?.inputType = InputType.TYPE_CLASS_NUMBER
        mLayoutTime.getEdView()?.setText(TimeUtil.getCurrentDate())
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = OysterListAdapter(this, oysterDataList)
        mRecyclerView.adapter = adapter
        adapter?.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {

            }

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
                    poiItem == null -> {
                        ToastUtil.showMessage("请输选择位置")
                    }
                    oysterDataList.size < 1 -> {
                        ToastUtil.showMessage("请输入生蚝信息")
                    }
                    TextUtils.isEmpty(mLayoutName.getEdString()) -> {
                        ToastUtil.showMessage("请输入养殖户姓名")
                    }
                    TextUtils.isEmpty(mLayoutPhone.getEdString()) -> {
                        ToastUtil.showMessage("请输入联系电话")
                    }
                    else -> {
                        var oysterIdList = ""
                        for (index in oysterDataList.indices) {
                            oysterIdList = if (index < oysterDataList.size - 1) {
                                oysterIdList + oysterDataList[index].id + ","
                            } else {
                                oysterIdList + oysterDataList[index].id
                            }
                        }
                        presenterImpl?.addOperate(
                            mLayoutNum.getEdString(),
                            mLayoutLabel.getEdString(),
                            poiItem?.latLonPoint?.longitude.toString(),
                            poiItem?.latLonPoint?.latitude.toString(),
                            mLayoutName.getEdString(),
                            mLayoutPhone.getEdString(),
                            oysterIdList
                        )
                    }
                }
            }
            R.id.mTvAddOyster -> {
                AddOysterActivity.start(this, REQUEST_CODE_OYSTER)
            }
            R.id.mLayoutMap -> {
                MapActivity.start(this, REQUEST_CODE_MAP)
            }

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK){
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
                    MarkerOptions().position(latLng).title(poiItem!!.title).snippet(poiItem?.adName + poiItem?.snippet)
                )
                marker.showInfoWindow()
            }
            REQUEST_CODE_OYSTER -> {
                val oysterData = data?.getParcelableExtra<OysterData>("OysterData")
                if (oysterData != null) {
                    oysterDataList.add(oysterData)
                }
                adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun getMarkerOptions(): MarkerOptions? {
        poiItem?.latLonPoint?.latitude ?: return null
        poiItem?.latLonPoint?.longitude ?: return null
        return MarkerOptions().position(
            LatLng(
                poiItem!!.latLonPoint!!.latitude,
                poiItem!!.latLonPoint!!.longitude
            )
        )
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


    override fun addOperate(bean: BaseBean?) {
        ToastUtil.showMessage("下苗成功")
        finish()
    }
}