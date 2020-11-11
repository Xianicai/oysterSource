package com.oystersource.utils.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.oystersource.R;
import com.oystersource.utils.UIUtil;

public class LoadingDialog {
    private Activity mContext;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mDialog;
    private View mView;
    private ImageView mImageView;

    /**
     * 按钮数量
     */
    private int mBtnNum = 1;
    /**
     * 是否已经初始化
     */
    private boolean mHasInit = false;

    private boolean mCancelable = false;

    private int mDialogWidth;

    public LoadingDialog(Activity context) {
        this.mContext = context;
        if (mContext != null && !mContext.isFinishing()) {
            mBuilder = new AlertDialog.Builder(context);
            mView = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);
            mImageView = (ImageView) mView.findViewById(R.id.mImageView);
            Glide.with(context).load(R.mipmap.ic_loading).into(mImageView);
            mBuilder.setView(mView);

        }
    }


    /**
     * 显示Dialog
     */
    public void show() {
        // 有可能创建ConfirmDialog的时候，Activity已经被销毁，mView就会为null
        // 这个时候调用show直接return即可
        if (mView == null) {
            return;
        }
        if (!mHasInit) {
            mDialog = mBuilder.create();
            mDialog.setCancelable(mCancelable);
            mDialog.setCanceledOnTouchOutside(mCancelable);
            mHasInit = true;
        }
        try {
            if (mContext != null && !mContext.isFinishing()) {
                mDialog.show();
                // 设置Dialog宽度

                WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
                lp.dimAmount=0.0f;
                lp.width = UIUtil.dp2px(mContext, 186);
                lp.height = UIUtil.dp2px(mContext, 158);
                mDialog.getWindow().setAttributes(lp);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isShowing() {
        return mDialog != null && mDialog.isShowing();
    }

    /**
     * 隐藏Dialog
     */
    public void dismiss() {
        if (mDialog!=null){
            mDialog.dismiss();
        }
    }

}
