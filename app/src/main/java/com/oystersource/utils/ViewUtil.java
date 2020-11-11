package com.oystersource.utils;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewUtil {

	public static boolean setTextSafe(TextView textView, String text) {
		if (textView != null) {
			textView.setText(text);
			return true;
		}
		return false;
	}

	/**
	 * text不为空，则显示textView，并设置text；否则隐藏textView
	 */
	public static boolean setTextOrHide(TextView textView, String text) {
		if (!TextUtils.isEmpty(text)) {
			textView.setText(text);
			textView.setVisibility(View.VISIBLE);
			return true;
		} else {
			textView.setVisibility(View.GONE);
			return false;
		}
	}

	/**
	 * num > 0，则显示textView，并显示num；否则隐藏textView
	 */
	public static boolean setNumberOrHide(TextView textView, int num) {
		if (num > 0) {
			textView.setText(String.valueOf(num));
			textView.setVisibility(View.VISIBLE);
			return true;
		} else {
			textView.setVisibility(View.GONE);
			return false;
		}
	}

	/**
	 * resId > 0，则显示imageView，并显示图片；否则隐藏imageView
	 */
	public static boolean setImageOrHide(ImageView imageView, int resId) {
		if (resId > 0) {
			imageView.setImageResource(resId);
			imageView.setVisibility(View.VISIBLE);
			return true;
		} else {
			imageView.setVisibility(View.GONE);
			return false;
		}
	}



	/**
	 * 为View设置宽高
	 */
	public static void setLayoutParams(View v, int width, int height) {
		LayoutParams layoutParams = v.getLayoutParams();
		if (layoutParams == null) {
			layoutParams = new LayoutParams(width, height);
		} else {
			layoutParams.width = width;
			layoutParams.height = height;
		}
		v.setLayoutParams(layoutParams);
	}

	/**
	 * 设置View隐藏/显示，View为空则不执行操作
	 */
	public static void setVisibility(View v, int visibility) {
		if (v != null && visibility != v.getVisibility()) {
			v.setVisibility(visibility);
		}
	}

	public static void show(boolean isShow, View... views) {
        if (isShow) {
            show(views);
        } else {
            hide(views);
        }
	}

	public static void show(View... views) {
		if (views != null && views.length > 0) {
			for (View view : views) {
				setVisibility(view, View.VISIBLE);
			}
		}
	}

	public static void hide(View... views) {
		if (views != null && views.length > 0) {
			for (View view : views) {
				setVisibility(view, View.GONE);
			}
		}
	}

	public static boolean isVisible(View v) {
		return v != null && v.getVisibility() == View.VISIBLE;
	}

	public static void setClick(View.OnClickListener l, View... views) {
		if (views != null && views.length > 0) {
			for (View view : views) {
				if (view == null) {
					continue;
				}
				view.setOnClickListener(l);
			}
		}
	}





}
