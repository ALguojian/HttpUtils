package com.shuwtech.commonsdk.ui.state;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewAnimator;

import com.shuwtech.commonsdk.R;

/**
 * 状态切换ViewAnimator
 * */
public class StateViewAnimator extends ViewAnimator {

    private final int DEFAULT_LAYOUT_NULL = 0;
    private final int DEFAULT_EMPTY_LAYOUT = R.layout.common_view_state_empty;
    private final int DEFAULT_LOADING_LAYOUT = R.layout.common_view_state_loading;
    private final int DEFAULT_ERROR_LAYOUT = R.layout.common_view_state_error;

    @LayoutRes
    private int mContentLayout;
    @LayoutRes
    private int mEmptyLayout;
    @LayoutRes
    private int mLoadingLayout;
    @LayoutRes
    private int mErrorLayout;

    public StateViewAnimator(Context context) {
        super(context);
        init(context, null);
    }

    public StateViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateViewAnimator);
        mContentLayout = a.getResourceId(R.styleable.StateViewAnimator_contentView, DEFAULT_LAYOUT_NULL);
        mEmptyLayout = a.getResourceId(R.styleable.StateViewAnimator_emptyView, DEFAULT_EMPTY_LAYOUT);
        mLoadingLayout = a.getResourceId(R.styleable.StateViewAnimator_loadingView, DEFAULT_LOADING_LAYOUT);
        mErrorLayout = a.getResourceId(R.styleable.StateViewAnimator_errorView, DEFAULT_ERROR_LAYOUT);
        a.recycle();
    }

    //inflate各个状态view
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (findViewById(R.id.stateContent) == null
            || mContentLayout != DEFAULT_LAYOUT_NULL) {
            View v = inflate(getContext(), mContentLayout, null);
            v.setId(R.id.stateContent);
            addView(v);
        }

        if (findViewById(R.id.stateLoading) == null) {
            View v = inflate(getContext(), mLoadingLayout, null);
            v.setId(R.id.stateLoading);
            addView(v);
        }

        if (findViewById(R.id.stateEmpty) == null) {
            View v = inflate(getContext(), mEmptyLayout, null);
            v.setId(R.id.stateEmpty);
            addView(v);
        }

        if (findViewById(R.id.stateError) == null) {
            View v = inflate(getContext(), mErrorLayout, null);
            v.setId(R.id.stateError);
            addView(v);
        }
    }


    //设置内容页面
    public void setContentLayout(@LayoutRes int contentLayout) {
        mContentLayout = contentLayout;
    }

    //设置空页面
    public void setEmptyLayout(@LayoutRes int emptyLayout) {
        mEmptyLayout = emptyLayout;
        if (findViewById(R.id.stateEmpty) == null) {
            View v = inflate(getContext(), mEmptyLayout, null);
            v.setId(R.id.stateEmpty);
            addView(v);
        }
    }

    //设置加载页面
    public void setLoadingLayout(@LayoutRes int loadingLayout) {
        mLoadingLayout = loadingLayout;
        if (findViewById(R.id.stateLoading) == null) {
            View v = inflate(getContext(), mLoadingLayout, null);
            v.setId(R.id.stateLoading);
            addView(v);
        }
    }

    //设置错误页面
    public void setErrorLayout(@LayoutRes int errorLayout) {
        mErrorLayout = errorLayout;
        if (findViewById(R.id.stateError) == null) {
            View v = inflate(getContext(), mErrorLayout, null);
            v.setId(R.id.stateError);
            addView(v);
        }
    }
}
