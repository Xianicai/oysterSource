package com.oystersource.utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ResetScrollView extends ScrollView {
    private OnScrollListener listener;

    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public ResetScrollView(Context context) {
        super(context);
    }

    public ResetScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResetScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnScrollListener{
        void onScroll(int scrollY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(listener != null){
            listener.onScroll(t);
        }
    }
}
