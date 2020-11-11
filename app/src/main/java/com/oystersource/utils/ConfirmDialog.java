package com.oystersource.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.oystersource.R;

public class ConfirmDialog {
    private Activity mContext;
    private android.app.AlertDialog.Builder mBuilder;
    private android.app.AlertDialog mDialog;
    private View mView;
    private Object mTag;
    // 标题
    private String mTitle;
    private String mDesc;
    private TextView mTvTitle;
    // 内容
    private TextView mTvDesc;
    private TextView mTvLeft;
    private TextView mTvRight;

    private String mLeftText;
    private OnConfirmDialogClickListener mBtn1Listener;
    private OnConfirmDialogClickListener mSingleBtnListener;
    private String mRightText;
    private OnConfirmDialogClickListener mBtn2Listener;
    private DialogInterface.OnDismissListener mOnDismissListener;
    /**
     * 按钮数量
     */
    private int mBtnNum = 1;
    /**
     * 是否已经初始化
     */
    private boolean mHasInit = false;

    private boolean mCancelable = true;

    private int mDialogWidth;
    private View mDoubleLayout;
    private TextView mTvIKnow;

    public ConfirmDialog(Activity context) {
        this.mContext = context;
        if (mContext != null && !mContext.isFinishing()) {
            mBuilder = new android.app.AlertDialog.Builder(context);
            mView = LayoutInflater.from(context).inflate(R.layout.confirm_dialog, null);
            mTvTitle = (TextView) mView.findViewById(R.id.mTvTitle);
            mTvDesc = (TextView) mView.findViewById(R.id.mLayoutQR);
            mTvLeft = (TextView) mView.findViewById(R.id.mTvLeft);
            mTvRight = (TextView) mView.findViewById(R.id.mTvRight);
            mTvIKnow = (TextView) mView.findViewById(R.id.mTvIKnow);
            mDoubleLayout = mView.findViewById(R.id.mDoubleLayout);
            mTvTitle.getPaint().setFakeBoldText(true);
            mBuilder.setView(mView);
            // 初始化Dialog的宽度，屏幕的6/7
            int width = context.getWindowManager().getDefaultDisplay().getWidth();
            mDialogWidth = width * 6 / 7;
        }
    }


    /**
     * 设置标题文字，假如title为空，则会隐藏该控件
     */
    public ConfirmDialog setTitle(String title) {
        this.mTitle = title;
        if (mHasInit) {
            ViewUtil.setTextOrHide(mTvTitle, title);
        }
        return this;
    }

    /**
     * 设置内容，假如message为空，则会隐藏该控件
     */
    public ConfirmDialog setMessage(String message) {
        this.mDesc = message;
        if (mHasInit) {
            ViewUtil.setTextOrHide(mTvDesc, message);
        }
        return this;
    }


    /**
     * 设置两个按钮Dialog的文字和点击事件，点击事件为空则会设置一个默认事件---点击关闭Dialog
     *
     * @param text1 第一个按钮的文字，为空则会设置默认的文字
     * @param l1    第一个按钮的点击事件
     * @param text2 第二个按钮的文字，为空则会设置默认的文字
     * @param l2    第二个按钮的点击事件
     */
    public ConfirmDialog setTwoButtonListener(String text1, OnConfirmDialogClickListener l1, String text2,
                                              OnConfirmDialogClickListener l2) {
        this.mLeftText = text1;
        this.mBtn1Listener = l1;
        this.mRightText = text2;
        this.mBtn2Listener = l2;
        mBtnNum = 2;
        return this;
    }

    /**
     * 设置单个按钮Dialog的点击事件(按钮为“知道了”)
     *
     * @param l
     *            点击事件，点击事件为空则会设置一个默认事件---点击关闭Dialog
     */
    public ConfirmDialog setSingleButtonListener(OnConfirmDialogClickListener l) {
        return setSingleButtonListener(null, l);
    }
    /**
     * 设置单个按钮Dialog的文字和点击事件
     *
     * @param text
     *            按钮文字，为空则会设置默认的文字
     * @param l
     *            点击事件，点击事件为空则会设置一个默认事件---点击关闭Dialog
     */
    public ConfirmDialog setSingleButtonListener(String text, OnConfirmDialogClickListener l) {
        this.mSingleBtnListener = l;
        mBtnNum = 1;
        return this;
    }



    /**
     * 设置两个按钮Dialog的点击事件(左边的按钮是“取消”，右边是“确定”)，点击事件为空则会设置一个默认事件---点击关闭Dialog
     *
     * @param l1 第一个按钮的点击事件
     * @param l2 第二个按钮的点击事件
     */
    public ConfirmDialog setTwoButtonListener(OnConfirmDialogClickListener l1, OnConfirmDialogClickListener l2) {
        return setTwoButtonListener(null, l1, null, l2);
    }

    public ConfirmDialog setOnDismissListener(DialogInterface.OnDismissListener l) {
        this.mOnDismissListener = l;
        return this;
    }

    public ConfirmDialog setCancelable(boolean flag) {
        this.mCancelable = flag;
        return this;
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
            // 初始化title
            ViewUtil.setTextOrHide(mTvTitle, mTitle);
            // 初始化message
            ViewUtil.setTextOrHide(mTvDesc, mDesc);
            // 初始化按钮
            if (!TextUtils.isEmpty(mLeftText)) {
                mTvLeft.setText(mLeftText);
            }
            if (mBtn1Listener == null) {
                mTvLeft.setOnClickListener(new DialogDismissListener());
            } else {
                mTvLeft.setOnClickListener(new OnDialogButtonClick(mBtn1Listener));
            }
            if (!TextUtils.isEmpty(mRightText)) {
                mTvRight.setText(mRightText);
            }
            if (mBtn2Listener == null) {
                mTvRight.setOnClickListener(new DialogDismissListener());
            } else {
                mTvRight.setOnClickListener(new OnDialogButtonClick(mBtn2Listener));
            }
            if (mSingleBtnListener == null) {
                mTvIKnow.setOnClickListener(new DialogDismissListener());
            } else {
                mTvIKnow.setOnClickListener(new OnDialogButtonClick(mSingleBtnListener));
            }
            mDialog = mBuilder.create();
            mDialog.setOnDismissListener(mOnDismissListener);
            mDialog.setCancelable(mCancelable);
            mDialog.setCanceledOnTouchOutside(mCancelable);
            mHasInit = true;
        }
        try {
            if (mContext != null && !mContext.isFinishing()) {
                mDialog.show();
//                // 设置Dialog宽度
                WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
                lp.width = mDialogWidth;
                lp.height = UIUtil.dp2px(mContext, 180);
                mDialog.getWindow().setAttributes(lp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isShowing() {
        return mDialog != null && mDialog.isShowing();
    }

    public ConfirmDialog isDoubleButton(boolean isDoubleButton) {
        if (isDoubleButton) {
            mTvIKnow.setVisibility(View.GONE);
            mDoubleLayout.setVisibility(View.VISIBLE);
        } else {
            mTvIKnow.setVisibility(View.VISIBLE);
            mDoubleLayout.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 隐藏Dialog
     */
    public void dismiss() {
        mDialog.dismiss();
    }

    private class DialogDismissListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mDialog.dismiss();
        }

    }

    public interface OnConfirmDialogClickListener {
        void onClick(ConfirmDialog dialog, View v);
    }

    private class OnDialogButtonClick implements View.OnClickListener {
        private OnConfirmDialogClickListener mOnConfirmDialogClickListener;

        public OnDialogButtonClick(OnConfirmDialogClickListener l) {
            this.mOnConfirmDialogClickListener = l;
        }

        @Override
        public void onClick(View v) {
            if (mOnConfirmDialogClickListener != null) {
                mOnConfirmDialogClickListener.onClick(ConfirmDialog.this, v);
            }
        }
    }
}
