package com.oystersource.home.view.adapter

import android.content.Context
import android.net.Uri
import com.amap.api.services.core.PoiItem
import com.bumptech.glide.Glide
import com.oystersource.R
import com.oystersource.utils.OnResultCallback
import com.oystersource.utils.adapter.CommonRecyclerAdapter
import com.oystersource.utils.adapter.ViewHolder
import kotlinx.android.synthetic.main.image_list_item.view.*
import kotlinx.android.synthetic.main.location_list_item.view.*

class LocationListAdapter(context: Context, list: ArrayList<PoiItem>) :
    CommonRecyclerAdapter<PoiItem>(context, list, R.layout.location_list_item) {

    override fun conver(holder: ViewHolder, item: PoiItem) {
        holder.run {
            itemView.mTvLocationName.text = item.title
            itemView.mTvLocation.text = item.cityName + item.adName + item.snippet
        }
    }
}