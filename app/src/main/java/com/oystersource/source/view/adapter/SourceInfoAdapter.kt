package com.oystersource.source.view.adapter

import android.content.Context
import android.net.Uri
import com.bumptech.glide.Glide
import com.oystersource.R
import com.oystersource.utils.OnResultCallback
import com.oystersource.utils.adapter.CommonRecyclerAdapter
import com.oystersource.utils.adapter.ViewHolder
import kotlinx.android.synthetic.main.image_list_item.view.*
import kotlinx.android.synthetic.main.source_list_item.view.*

class SourceInfoAdapter(context: Context, list: ArrayList<String>) :
    CommonRecyclerAdapter<String>(context, list, R.layout.source_list_item) {

    var callback: OnResultCallback<String>? = null
    override fun conver(holder: ViewHolder, item: String) {
       holder.run {

       }
    }

}