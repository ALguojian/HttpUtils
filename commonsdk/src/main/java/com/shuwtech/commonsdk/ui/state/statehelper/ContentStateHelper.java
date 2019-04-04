package com.shuwtech.commonsdk.ui.state.statehelper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import com.shuwtech.commonsdk.R;
import com.shuwtech.commonsdk.ui.state.StateViewAnimator;

/**
 * 一般页面状态切换帮助类
 * */
public class ContentStateHelper implements IStateHelper {

    private StateViewAnimator mViewAnimator;

    private int mLoadingPosition;
    private int mContentPosition;
    private int mEmptyPosition;
    private int mErrPosition;

    public ContentStateHelper(StateViewAnimator stateViewAnimator) {
        if (stateViewAnimator != null) ;
        mViewAnimator = stateViewAnimator;
        mLoadingPosition = mViewAnimator.indexOfChild(mViewAnimator.findViewById(R.id.stateLoading));
        mContentPosition = mViewAnimator.indexOfChild(mViewAnimator.findViewById(R.id.stateContent));
        mEmptyPosition = mViewAnimator.indexOfChild(mViewAnimator.findViewById(R.id.stateEmpty));
        mErrPosition = mViewAnimator.indexOfChild(mViewAnimator.findViewById(R.id.stateError));
    }

    public ContentStateHelper(Context context) {
        mViewAnimator = new StateViewAnimator(context);
        mViewAnimator.setId(R.id.contentLoader);

        mViewAnimator.setLoadingLayout(R.layout.common_view_state_loading);
        mViewAnimator.setEmptyLayout(R.layout.common_view_state_empty);
        mViewAnimator.setErrorLayout(R.layout.common_view_state_error);
        mViewAnimator.requestLayout();

        if (mViewAnimator != null) {
            mLoadingPosition = mViewAnimator.indexOfChild(mViewAnimator.findViewById(R.id.stateLoading));
            mContentPosition = mViewAnimator.indexOfChild(mViewAnimator.findViewById(R.id.stateContent));
            mEmptyPosition = mViewAnimator.indexOfChild(mViewAnimator.findViewById(R.id.stateEmpty));
            mErrPosition = mViewAnimator.indexOfChild(mViewAnimator.findViewById(R.id.stateError));
        }
    }

    @Override
    public void showLoading() {
        mViewAnimator.setDisplayedChild(mLoadingPosition);
    }

    @Override
    public void showContent() {
        mViewAnimator.setDisplayedChild(mContentPosition);
    }

    @Override
    public void showEmpty() {
        mViewAnimator.setDisplayedChild(mEmptyPosition);
    }

    @Override
    public void showError() {
        mViewAnimator.setDisplayedChild(mErrPosition);
    }

    @Override
    public ViewAnimator getStateViewAnimator() {
        return mViewAnimator;
    }

    public void setContentView(View contentView) {
        if (contentView == null) return;

        ViewGroup parent = (ViewGroup) contentView.getParent();
        if (parent == null) return;

        if (mViewAnimator.findViewById(R.id.stateContent) != null) {
            mViewAnimator.removeView(mViewAnimator.findViewById(R.id.stateContent));
        }

        ViewGroup.LayoutParams lp = contentView.getLayoutParams();
        int index = parent.indexOfChild(contentView);
        parent.removeView(contentView);
        parent.addView(mViewAnimator, index, lp);

        contentView.setLayoutParams(new StateViewAnimator.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mViewAnimator.addView(contentView);
        mContentPosition = mViewAnimator.indexOfChild(contentView);
        showContent();
    }
}
