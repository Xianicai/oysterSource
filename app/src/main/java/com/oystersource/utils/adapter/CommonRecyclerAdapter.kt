package com.oystersource.utils.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class CommonRecyclerAdapter<T>(
    context: Context,
    datas: MutableList<T>,
    layoutId: Int,
    multiTypeSupport: MultiTypeSupport<T>?
) : RecyclerView.Adapter<ViewHolder>() {


    constructor(context: Context, datas: MutableList<T>, layoutId: Int) : this(context, datas, layoutId, null)
    constructor(context: Context, datas: MutableList<T>, multiTypeSupport: MultiTypeSupport<T>) :
            this(context, datas, 0, multiTypeSupport)

    protected var mContext = context
    private var mInflater: LayoutInflater = LayoutInflater.from(mContext)
    //数据怎么办？利用泛型
    protected var mDatas = datas
    // 布局怎么办？直接从构造里面传递
    private var mLayoutId: Int = layoutId
    // 多布局支持
    private val mMultiTypeSupport = multiTypeSupport

    override fun getItemViewType(position: Int): Int {
        if (mMultiTypeSupport != null) {
            return mMultiTypeSupport.getLayoutId(mDatas[position], position)
        }
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 多布局支持
        if (mMultiTypeSupport != null) {
            mLayoutId = viewType
        }

        //先inflate 数据
        val itemViwe = mInflater.inflate(mLayoutId, parent, false)
        return ViewHolder(itemViwe)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 设置点击和长按事件
        holder.itemView.setOnClickListener { mItemClickListener?.onItemClick(position) }
//        holder.itemView.setOnLongClickListener { mLongClickListener?.onItemLongClick(position) }
        conver(holder, mDatas[position])
    }


    override fun getItemCount(): Int {
        return mDatas.size
    }

    abstract fun conver(holder: ViewHolder, item: T)

    /***************
     * 设置条目点击和长按事件
     *********************/
    var mItemClickListener: OnItemClickListener? = null
    var mLongClickListener: OnItemLongClickListener? = null
    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    fun setOnLongClickListener(longClickListener: OnItemLongClickListener) {
        mLongClickListener = longClickListener
    }
}

