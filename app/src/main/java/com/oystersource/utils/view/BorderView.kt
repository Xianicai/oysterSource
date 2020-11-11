package com.oystersource.utils.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.oystersource.R


/**
 * 用来为View增加边框，可以设置边框的颜色以及边框的填充色。
 * <BorderView
 *      android:layout_width="200dp"
 *      android:layout_height="50dp"
 *      app:borderViewStrokeColor="@android:color/holo_blue_bright"
 *      app:borderViewRadius="3dp"
 *      app:borderViewBackgroundColor="#999999"
 *      app:borderViewText="文字"
 *      app:borderViewTextSize="16sp"/>
 *
 * 由于本身是继承自RelativeLayout，如果不想用默认的样式，可以直接增加子元素
 * <BorderView
 *      android:layout_width="200dp"
 *      android:layout_height="50dp"
 *      android:gravity="center"
 *      app:borderViewRounded="true"
 *      app:borderViewStrokeColor="@android:color/holo_blue_bright">
 *
 *      <TextView
 *          android:layout_width="wrap_content"
 *          android:layout_height="wrap_content"
 *          android:text="文字"/>
 * </BorderView>
 *
 * 自定义属性：
 * borderViewStrokeColor：边框色
 * borderViewStrokeSelectedColor：selected为true时的边框色
 * borderViewStrokeWidth：边框宽度
 * borderViewBackgroundColor：背景色
 * borderViewBackgroundSelectedColor：selected为true时的背景色
 * borderViewRadius：圆角半径
 * borderViewRounded：左右两边设置为圆形
 * borderViewText：文字
 * borderViewTextColor：文字颜色
 * borderViewTextSelectedColor：selected为true时的文字颜色
 * borderViewTextSize：文字大小
 * borderViewStrokePadding：边框到内部背景的间距
 */

class BorderView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
        RelativeLayout(context, attrs, defStyleAttr) {
    private var strokePaint: Paint? = null
    private var backgroundPaint: Paint? = null
    private var strokeWidth1: Float = 5f
    private var strokeColor: Int = 0
    private var mBackgroundColor: Int = 0
    private var radius: Float = 13f
    private var strokeHalfWidth: Float = 0f
    private var rounded: Boolean = false
    private var mText: String? = null
    private var mTextColor: Int = 0
    private var mTextSize: Float = 0f
    private var strokePadding: Float = 5f
    private var strokeSelectedColor: Int = 0
    private var backgroundSelectedColor: Int = 0
    private var mTextSelectedColor: Int = 0
    private var textView: TextView? = null


    constructor(context: Context?) : this(context, null, 0)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        parseAttrs(attrs)
        buildChildView()
    }

    private fun parseAttrs(attrs: AttributeSet?) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BorderView)
        strokeColor = typedArray.getColor(R.styleable.BorderView_borderViewStrokeColor, 0)
        if (strokeColor != 0) {
            strokeWidth1 = typedArray.getDimension(R.styleable.BorderView_borderViewStrokeWidth, dp2px(1f))
            strokeHalfWidth = strokeWidth1 / 2
        }
        radius = typedArray.getDimension(R.styleable.BorderView_borderViewRadius, dp2px(20f))
        strokePadding = typedArray.getDimension(R.styleable.BorderView_borderViewStrokePadding, 0f)
        mBackgroundColor = typedArray.getColor(R.styleable.BorderView_borderViewBackgroundColor, 0)
        rounded = typedArray.getBoolean(R.styleable.BorderView_borderViewRounded, false)
        mText = typedArray.getString(R.styleable.BorderView_borderViewText)
        mTextColor = typedArray.getColor(R.styleable.BorderView_borderViewTextColor, 0)
        mTextSize = typedArray.getDimension(R.styleable.BorderView_borderViewTextSize, 0f)
        strokeSelectedColor = typedArray.getColor(R.styleable.BorderView_borderViewStrokeSelectedColor, 0)
        backgroundSelectedColor = typedArray.getColor(R.styleable.BorderView_borderViewBackgroundSelectedColor, 0)
        mTextSelectedColor = typedArray.getColor(R.styleable.BorderView_borderViewTextSelectedColor, 0)
        typedArray.recycle()
    }

    /**
     * 设置文字
     * */
    private fun buildChildView() {
        mText ?: return
        textView = TextView(context).apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
            }
            setSingleLine()
            gravity = Gravity.CENTER
            if (mTextColor != 0) {
                setTextColor(getTextColorInternal())
            }
            if (mTextSize > 0) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
            }
            text = mText
        }
        addView(textView)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        //画背景
        val backgroundRect = RectF(
            strokeWidth1 + strokePadding,
            strokeWidth1 + strokePadding,
                measuredWidth - strokeWidth1 - strokePadding,
                measuredHeight - strokeWidth1 - strokePadding
        )
        val heightHalf = measuredHeight / 2
        backgroundPaint = createBackgroundPaint()
        when {
            rounded -> canvas?.drawRoundRect(backgroundRect, heightHalf.toFloat(), heightHalf.toFloat(), backgroundPaint!!)
            radius > 0 -> canvas?.drawRoundRect(backgroundRect, radius, radius, backgroundPaint!!)
            else -> canvas?.drawRect(backgroundRect, backgroundPaint!!)
        }
        //画边框
        val strokeRect = RectF(
                strokeHalfWidth,
                strokeHalfWidth,
                measuredWidth - strokeHalfWidth,
                measuredHeight - strokeHalfWidth
        )
        strokePaint = createStrokePaint()

        when {
            rounded -> canvas?.drawRoundRect(strokeRect, heightHalf.toFloat(), heightHalf.toFloat(), strokePaint!!)
            radius > 0 -> canvas?.drawRoundRect(strokeRect, radius, radius, strokePaint!!)
            else -> canvas?.drawRect(strokeRect, strokePaint!!)
        }
        super.dispatchDraw(canvas)
    }

    override fun dispatchSetSelected(selected: Boolean) {
        super.dispatchSetSelected(selected)
        strokePaint?.run {
            color = getStrokeColorInternal()
        }
        backgroundPaint?.run {
            color = getBackgroundColorInternal()
        }
        textView?.run {
            if (mTextSelectedColor != 0) {
                setTextColor(getTextColorInternal())
            }
        }
    }

    private fun getStrokeColorInternal(): Int {
        return if (isSelected) strokeSelectedColor else strokeColor
    }

    private fun getBackgroundColorInternal(): Int {
        return if (isSelected) backgroundSelectedColor else mBackgroundColor
    }

    private fun getTextColorInternal(): Int {
        return if (isSelected) mTextSelectedColor else mTextColor
    }


    private fun createStrokePaint(): Paint {
        if (strokePaint == null) {
            strokePaint = Paint().apply {
                isAntiAlias = true
                color = getStrokeColorInternal()
                strokeWidth = strokeWidth1
                style = Paint.Style.STROKE
                strokeCap = Paint.Cap.ROUND
            }
        }
        return strokePaint as Paint
    }

    private fun createBackgroundPaint(): Paint {
        if (backgroundPaint == null) {
            backgroundPaint = Paint().apply {
                color = getBackgroundColorInternal()
                isAntiAlias = true
                style = Paint.Style.FILL
                strokeCap = Paint.Cap.ROUND
            }
        }
        return backgroundPaint as Paint
    }

    private fun dp2px(dpValue: Float): Float {
        return (dpValue * Resources.getSystem().displayMetrics.density + 0.5f)
    }

    /**
     * 设置文字
     */
    fun setText(text: String) {
        mText = text
        textView?.run {
            setText(mText)
        }

    }

    /**
     * 设置文字颜色
     */
    fun setTextColor(textColor: Int) {
        mTextColor = textColor
        textView?.run {
            setTextColor(getTextColorInternal())
        }
    }

    /**
     * 设置文字大小
     */
    fun setTextSize(textSize: Float) {
        mTextSize = textSize
        textView?.run {
            if (mTextSize > 0) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
            }
        }
    }

    /**
     * 设置内部背景颜色
     */
    fun setMBackgroundColor(color: Int) {
        mBackgroundColor = color
        backgroundPaint?.run {
            setColor(getBackgroundColorInternal())
            postInvalidate()
        }
    }

    /**
     * 设置边框颜色
     */
    fun setStrokeColor(color: Int) {
        strokeColor = color
        strokePaint?.run {
            setColor(getStrokeColorInternal())
            postInvalidate()
        }
    }

    /**
     * 设置边框宽度
     */
    fun setStrokeWidth(strokeWidth: Float) {
        this.strokeWidth1 = strokeWidth
        strokePaint?.run {
            setStrokeWidth(strokeWidth)
            postInvalidate()
        }
    }
}

