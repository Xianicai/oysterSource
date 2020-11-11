package com.oystersource.utils.adapter


import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class WrapRecyclerView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : RecyclerView(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    // 包裹了一层的头部底部Adapter
    private var mWrapRecyclerAdapter: WrapRecyclerAdapter? = null
    // 这个是列表数据的Adapter
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private val mDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            mAdapter ?: return
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyDataSetChanged没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter?.notifyDataSetChanged()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            mAdapter ?: return
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyDataSetChanged没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter?.notifyItemRemoved(positionStart)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            mAdapter ?: return
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemMoved没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter?.notifyItemMoved(fromPosition, toPosition)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            mAdapter ?: return
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemChanged没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter?.notifyItemChanged(positionStart)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            mAdapter ?: return
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemChanged没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter?.notifyItemChanged(positionStart, payload)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            mAdapter ?: return
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemInserted没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter?.notifyItemInserted(positionStart)
        }
    }

//    init {
//    }


    override fun setAdapter(adapter: Adapter<RecyclerView.ViewHolder>?) {
        //防止多次设置adapter
        mAdapter?.unregisterAdapterDataObserver(mDataObserver)
        mAdapter = null
        this.mAdapter = adapter
        mWrapRecyclerAdapter = adapter as? WrapRecyclerAdapter ?:
                WrapRecyclerAdapter(adapter!!)
        super.setAdapter(mWrapRecyclerAdapter)
        //注册观察者
        mAdapter?.registerAdapterDataObserver(mDataObserver)
        // 解决GridLayout添加头部和底部也要占据一行
        mWrapRecyclerAdapter!!.adjustSpanSize(this)
    }

    // 添加头部
    fun addHeaderView(view: View) {
        // 如果没有Adapter那么就不添加，也可以选择抛异常提示
        // 让他必须先设置Adapter然后才能添加，这里是仿照ListView的处理方式
            mWrapRecyclerAdapter?.addHeaderView(view)
    }

    // 添加底部
    fun addFooterView(view: View) {
            mWrapRecyclerAdapter?.addFooterView(view)
    }

    // 移除头部
    fun removeHeaderView(view: View) {
            mWrapRecyclerAdapter?.removeHeaderView(view)
    }

    // 移除底部
    fun removeFooterView(view: View) {
            mWrapRecyclerAdapter?.removeFooterView(view)
    }
}