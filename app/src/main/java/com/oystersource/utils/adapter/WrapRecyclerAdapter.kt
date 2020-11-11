package com.oystersource.utils.adapter

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WrapRecyclerAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        // 基本的头部类型开始位置  用于viewType
        private var BASE_ITEM_TYPE_HEADER = 10000000
        // 基本的底部类型开始位置  用于viewType
        private var BASE_ITEM_TYPE_FOOTER = 20000000
    }

    /**
     * 存放头部类型view
     */
    private var mHeaderViews: SparseArray<View> = SparseArray()
    /**
     * 存放底部类型view
     */
    private var mFooterViews: SparseArray<View> = SparseArray()

    private val mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder> = adapter

    override fun getItemCount(): Int {
        // 条数三者相加 = 底部条数 + 头部条数 + Adapter的条数
        return mAdapter.itemCount + mHeaderViews.size() + mFooterViews.size()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            isHeaderPosition(position) -> mHeaderViews.keyAt(position)
            isFooterPosition(position) -> mFooterViews.keyAt(position - mHeaderViews.size() - mAdapter.itemCount)
            else -> mAdapter.getItemViewType(position - mHeaderViews.size())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when {
            isHeaderViewType(viewType) -> createHeaderFooterViewHolder(mHeaderViews.get(viewType))
            isFooterViewType(viewType) -> createHeaderFooterViewHolder(mFooterViews.get(viewType))
            else -> mAdapter.onCreateViewHolder(parent, viewType) as ViewHolder
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return
        }
        mAdapter.onBindViewHolder(holder, position - mHeaderViews.size())
    }

    /**
     * 是不是头部类型
     * */
    private fun isHeaderViewType(viewType: Int): Boolean {
        return mHeaderViews.indexOfKey(viewType) >= 0
    }

    /**
     * 是不是底部类型
     * */
    private fun isFooterViewType(viewType: Int): Boolean {
        return mFooterViews.indexOfKey(viewType) >= 0
    }

    /**
     * 创建头部或者底部的ViewHolder
     */
    private fun createHeaderFooterViewHolder(view: View): ViewHolder {
        return object : ViewHolder(view) {}
    }

    /**
     * 是不是底部位置
     */
    private fun isFooterPosition(position: Int): Boolean {
        return position >= mHeaderViews.size() + mAdapter.itemCount
    }

    /**
     * 是不是头部位置
     */
    private fun isHeaderPosition(position: Int): Boolean {
        return position < mHeaderViews.size()
    }

    /*****************      外部调用的方法       ******************/

    /**
     * 添加头部
     * */
    fun addHeaderView(view: View) {
        val position = mHeaderViews.indexOfValue(view)
        if (position < 0) {
            mHeaderViews.put(BASE_ITEM_TYPE_HEADER++, view)
        }
        notifyDataSetChanged()
    }

    /**
     * 添加底部
     */
    fun addFooterView(view: View) {
        val position = mFooterViews.indexOfValue(view)
        if (position < 0) {
            mFooterViews.put(BASE_ITEM_TYPE_FOOTER++, view)
        }
        notifyDataSetChanged()
    }

    /**
     * 移除头部
     */
    fun removeHeaderView(view: View) {
        val index = mHeaderViews.indexOfValue(view)
        if (index < 0) return
        mHeaderViews.removeAt(index)
        notifyDataSetChanged()
    }

    /**
     * 移除底部
     */
    fun removeFooterView(view: View) {
        val index = mFooterViews.indexOfValue(view)
        if (index < 0) return
        mFooterViews.removeAt(index)
        notifyDataSetChanged()
    }

    /**
     * 解决GridLayoutManager添加头部和底部不占用一行的问题
     *
     * @param recycler
     * @version 1.0
     */
    fun adjustSpanSize(recycler: RecyclerView) {
        if (recycler.layoutManager is GridLayoutManager) {
            val layoutManager = recycler.layoutManager as GridLayoutManager?
            layoutManager!!.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val isHeaderOrFooter = isHeaderPosition(position) || isFooterPosition(position)
                    return if (isHeaderOrFooter) layoutManager.spanCount else 1
                }
            }
        }
    }



}