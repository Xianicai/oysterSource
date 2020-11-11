package com.oystersource.utils.adapter

interface MultiTypeSupport<T> {
    // 根据当前位置或者条目数据返回布局
    fun getLayoutId(item: T, position: Int): Int
}