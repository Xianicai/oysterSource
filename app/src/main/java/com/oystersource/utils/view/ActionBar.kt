package com.oystersource.utils.view

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.oystersource.R
import com.oystersource.utils.OnResultCallback
import kotlinx.android.synthetic.main.layout_action_bar.view.*

class ActionBar(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    RelativeLayout(context, attrs, defStyleAttr) {

    private var actionTitle: String? = null
    private var hintBackImage: Boolean = false
    private var onResultCallback: OnResultCallback<Any>? = null


    constructor(context: Context?) : this(context, null, 0)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        parseAttrs(attrs)
        buildView()
    }


    private fun parseAttrs(attrs: AttributeSet?) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActionBar)
        actionTitle = typedArray.getString(R.styleable.ActionBar_actionTitle)
        hintBackImage = typedArray.getBoolean(R.styleable.ActionBar_hintBackImage, false)
        typedArray.recycle()
    }

    private fun buildView() {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_action_bar, this)
        view.mTvActionTitle.text = actionTitle
        view.mImageBack.visibility = if (hintBackImage) View.GONE else View.VISIBLE
        view.mImageBack.setOnClickListener {
            onResultCallback?.call(it)
        }
    }

    fun setBackOnListener(onResultCallback: OnResultCallback<Any>?) {
        this.onResultCallback = onResultCallback
    }
}