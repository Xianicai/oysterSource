package com.oystersource.home.view.adapter

import android.content.Context
import android.net.Uri
import com.amap.api.services.core.PoiItem
import com.bumptech.glide.Glide
import com.oystersource.R
import com.oystersource.home.model.bean.OysterBean
import com.oystersource.home.model.bean.OysterData
import com.oystersource.home.model.bean.imgList
import com.oystersource.utils.OnResultCallback
import com.oystersource.utils.adapter.CommonRecyclerAdapter
import com.oystersource.utils.adapter.ViewHolder
import kotlinx.android.synthetic.main.image_list_item.view.*
import kotlinx.android.synthetic.main.image_list_item.view.mImageCover
import kotlinx.android.synthetic.main.location_list_item.view.*
import kotlinx.android.synthetic.main.oyster_list_item.view.*

class OysterListAdapter(context: Context, list: ArrayList<OysterData>) :
    CommonRecyclerAdapter<OysterData>(context, list, R.layout.oyster_list_item) {

    override fun conver(holder: ViewHolder, item: OysterData) {
        holder.run {
            itemView.mImageCover.setImage(item.imgList?.get(0)?.img)
            itemView.mTvCount.text = item.amount
            itemView.mTvState.text = item.state
            itemView.mTvTime.text = item.addTime
        }
    }
}