package com.oystersource.home.view.adapter

import android.content.Context
import android.net.Uri
import com.bumptech.glide.Glide
import com.oystersource.R
import com.oystersource.utils.OnResultCallback
import com.oystersource.utils.adapter.CommonRecyclerAdapter
import com.oystersource.utils.adapter.ViewHolder
import kotlinx.android.synthetic.main.image_list_item.view.*

class ImageListAdapter(context: Context, list: ArrayList<Uri>) :
    CommonRecyclerAdapter<Uri>(context, list, R.layout.image_list_item) {

    var callback: OnResultCallback<Uri>? = null
    override fun conver(holder: ViewHolder, item: Uri) {
        Glide.with(holder.itemView.context).load(item).into(holder.itemView.mImageCover)
        holder.itemView.mImageDelete.setOnClickListener {
            mDatas.remove(item)
            notifyDataSetChanged()
            callback?.call(item)
        }
    }

    fun setDeleteImageLister(callback: OnResultCallback<Uri>) {
        this.callback = callback
    }
}