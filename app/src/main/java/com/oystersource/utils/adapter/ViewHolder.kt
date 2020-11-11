package com.oystersource.utils.adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mViews: SparseArray<View> = SparseArray()
    private val mItemView: View = itemView


    /**
     * 通过id获取view
     */
    public fun <T : View> getView(viewId: Int): T {
        //先从缓存当中找
        var view = mViews.get(viewId)
        //如果没有，直接从ItemView中找
        if (view == null) {
            view = mItemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    /**
     * 设置TextView文本
     */
    fun setText(viewId: Int, text: CharSequence): ViewHolder {
        getView<TextView>(viewId).text = text
        return this
    }

    /**
     * 设置View的Visibility
     */
    fun setViewVisibility(viewId: Int, visibility: Int): ViewHolder {
        getView<View>(viewId).visibility = visibility
        return this
    }

    /**
     * 设置ImageView的资源
     */
    fun setImageResource(viewId: Int, resourceId: Int): ViewHolder {
        val imageView = getView<ImageView>(viewId)
        imageView.setImageResource(resourceId)
        return this
    }


    /**
     * 设置图片
     */
    fun setImageBitmap(viewId: Int, bitmap: Bitmap): ViewHolder {
        val imageView = getView<ImageView>(viewId)
        imageView.setImageBitmap(bitmap)
        return this
    }

    /**
     * 设置图片通过路径,这里稍微处理得复杂一些，因为考虑加载图片的第三方可能不太一样
     * 也可以直接写死
     */
    fun setImageByUrl(context: Context, viewId: Int, url: String): ViewHolder {
        val imageView = getView(viewId) as ImageView
        if (url.isNotEmpty()) {
            Glide.with(context)
                .load(url)
                .into(imageView)
        }

        return this
    }
}