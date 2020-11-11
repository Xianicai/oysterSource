package com.oystersource.source.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.core.PoiItem
import com.oystersource.R
import com.oystersource.base.BaseBean
import com.oystersource.base.view.BaseMVPActivity
import com.oystersource.home.presenter.BreedPresenterImpl
import com.oystersource.home.presenter.Contract.BreedContract
import com.oystersource.person.presenter.contract.SourceContract
import com.oystersource.source.presenter.SourceInfoPresenterImpl
import com.oystersource.source.view.adapter.SourceInfoAdapter
import com.oystersource.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_source.*


class SourceInfoActivity : BaseMVPActivity<SourceInfoPresenterImpl>(),
    SourceContract.SourceView,
    View.OnClickListener {
    companion object {
        fun start(context: Context?) {
            context?.startActivity(
                Intent(context, SourceInfoActivity::class.java)
            )
        }
    }
    private var mSelected = ArrayList<String>()
    var adapter: SourceInfoAdapter? = null
    var presenterImpl: SourceInfoPresenterImpl? = null
    var poiItem: PoiItem? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_source
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        // 保存地图当前状态
        mMapView.onSaveInstanceState(outState)
    }


    override fun createPresenter(): SourceInfoPresenterImpl {
        presenterImpl = SourceInfoPresenterImpl()
        return presenterImpl!!
    }

    override fun initViews(savedInstanceState: Bundle?) {
        initMap(savedInstanceState)
        mActionBar.setBackOnListener {
            onBackPressed()
        }
        mLayoutNum.getEdView()?.isEnabled = false
        mLayoutLabel.getEdView()?.isEnabled = false
        mLayoutMap.getEdView()?.isEnabled = false
        mSelected.add("1")
        mSelected.add("1")
        mSelected.add("1")
        mSelected.add("1")
        mSelected.add("1")
        mSelected.add("1")
        mSelected.add("1")
        mSelected.add("1")

        mRecyclerView.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        adapter = SourceInfoAdapter(this, mSelected)
        mRecyclerView.adapter = adapter
        presenterImpl?.getSourceInfo()
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
                        ToastUtil.showMessage("请输入选择养殖位置")
                    }
                    mSelected.size < 1 -> {
                        ToastUtil.showMessage("请选择图片")
                    }
                    else -> {


                    }
                }
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



    override fun getSourceInfo(bean: BaseBean?) {
    }
}