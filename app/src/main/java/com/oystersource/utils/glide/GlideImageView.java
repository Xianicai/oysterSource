package com.oystersource.utils.glide;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oystersource.R;

/**
 * Created by Zhanglibin on 2017/3/30.
 */

public class GlideImageView extends AppCompatImageView {

    private Context mCon;
    private int mDefaultImageResId;

    public GlideImageView(Context context) {
        this(context, null);
    }

    public GlideImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlideImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCon = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GlideImageView);
        mDefaultImageResId = ta.getResourceId(R.styleable.GlideImageView_placeholderImage, 0);
        ta.recycle();
        if (mDefaultImageResId != 0) {
            setImageResource(mDefaultImageResId);
        }

    }

    public void setImage(String url) {
            Glide.with(mCon)
                    .load(url)
                    .placeholder(mDefaultImageResId)
                    //图片获取失败时默认显示的图片
                    .error(mDefaultImageResId)
                    //缓存全尺寸图片，也缓存其他尺寸图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .crossFade()
                    .into(this);

    }

    public void setDefaultImage(int backgroud) {
        mDefaultImageResId = backgroud;

    }

    /**
     * 设置圆角图片
     */
    public void setRoundsImage(String url, int round) {
        if (mDefaultImageResId != 0) {

            Glide.with(mCon)
                    .load(url)
                    .placeholder(mDefaultImageResId)
                    .error(mDefaultImageResId)
                    //设置缓存
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideRoundTransform(mCon, round))
                    .into(this);
        }
    }

    /**
     * 设置圆形图片
     */
    public void setRoundedImage(String url) {
        if (mDefaultImageResId != 0) {
            Glide.with(mCon)
                    .load(url)
                    .placeholder(mDefaultImageResId)
                    .error(mDefaultImageResId)
                    //设置缓存
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideCircleTransform(mCon))
                    .into(this);
        }

    }

}
