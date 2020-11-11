package com.oystersource.utils.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import com.oystersource.R
import com.oystersource.utils.OnResultCallback
import kotlinx.android.synthetic.main.layout_custom_title_edit.view.*

class CustomTitleEditLayout(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    RelativeLayout(context, attrs, defStyleAttr) {

    private var actionTitle: String? = null
    private var customEdHint: String? = null
    private var onResultCallback: OnResultCallback<Any>? = null
    private var mLayoutCustom: View? = null


    constructor(context: Context?) : this(context, null, 0)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        parseAttrs(attrs)
        buildView()
    }


    private fun parseAttrs(attrs: AttributeSet?) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleEditLayout)
        actionTitle = typedArray.getString(R.styleable.CustomTitleEditLayout_customTitle)
        customEdHint = typedArray.getString(R.styleable.CustomTitleEditLayout_customEdHint)
        typedArray.recycle()
    }

    private fun buildView() {
        mLayoutCustom =
            LayoutInflater.from(context).inflate(R.layout.layout_custom_title_edit, this)
        mLayoutCustom?.mTvTitle?.text = actionTitle
        mLayoutCustom?.mEdView?.hint = customEdHint
    }

    fun getEdString(): String? {
        return mLayoutCustom?.mEdView?.text.toString()
    }

    fun getEdView(): EditText? {
        return mLayoutCustom?.mEdView
    }
}