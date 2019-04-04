package com.shuwtech.commonsdk.ui;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.shuwtech.commonsdk.R;
import com.shuwtech.commonsdk.glide.GlideUtils;
import com.shuwtech.commonsdk.ui.state.StateViewAnimator;
import com.shuwtech.commonsdk.ui.state.statehelper.ContentStateHelper;
import com.shuwtech.commonsdk.ui.state.statehelper.IStateHelper;


public class StateFragment extends com.shuwtech.commonsdk.ui.RxFragment implements IStateHelper {

    protected IStateHelper mIStateHelper;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpStateHelper(view);
    }

    protected void setUpStateHelper(View root) {
        if (mIStateHelper != null) return;

        View contentLoader = root.findViewById(R.id.contentLoader);
        if (contentLoader != null
            && contentLoader instanceof StateViewAnimator) {
            mIStateHelper = new ContentStateHelper((StateViewAnimator) contentLoader);
            return;
        }

        View stateContent = root.findViewById(R.id.stateContent);
        if (stateContent != null) {
            mIStateHelper = new ContentStateHelper(getContext());
            ((ContentStateHelper) mIStateHelper).setContentView(stateContent);
        }

        setStateLoadImage();
        setStateEmptyImage();
        setNetErrorClickEvent();
    }

    protected void setStateLoadImage() {
        if (getStateViewAnimator() == null) return;

        ImageView imgLoading = getStateViewAnimator().findViewById(R.id.imgStateLoading);
        if (imgLoading != null) {
            GlideUtils.displayGif(getContext(), R.drawable.common_ic_loading, imgLoading);
        }
    }

    protected void setStateEmptyImage() {
        if (getStateViewAnimator() == null) return;

        ImageView imgEmpty = getStateViewAnimator().findViewById(R.id.imgStateEmpty);
        GlideUtils.display(getContext(), R.drawable.common_ic_state_empty, imgEmpty);
    }

    protected void setStateEmptyImage(@DrawableRes int stateEmptyImage) {
        if (getStateViewAnimator() == null) return;

        ImageView imgEmpty = getStateViewAnimator().findViewById(R.id.imgStateEmpty);
        GlideUtils.display(getContext(), stateEmptyImage, imgEmpty);
    }

    protected void setStateEmptyText(@StringRes int stateEmptyTextRes) {
        if (getStateViewAnimator() == null) return;
        TextView tvEmpty = getStateViewAnimator().findViewById(R.id.tvStateEmpty);
        tvEmpty.setText(stateEmptyTextRes);
    }

    protected void setStateEmptyText(String stateEmptyText) {
        if (getStateViewAnimator() == null) return;
        TextView tvEmpty = getStateViewAnimator().findViewById(R.id.tvStateEmpty);
        tvEmpty.setText(stateEmptyText);
    }

    protected void setStateTipText(String text) {
        if (getStateViewAnimator() == null) return;
        if (TextUtils.isEmpty(text)) return;
        TextView tvTip = getStateViewAnimator().findViewById(R.id.tvStateTip);
        tvTip.setText(text);
        tvTip.setVisibility(View.VISIBLE);
    }

    protected void setNetErrorClickEvent() {
        if (getStateViewAnimator() == null) return;
        View view = getStateViewAnimator().findViewById(R.id.stateError).findViewById(R.id.llError);
        view.setOnClickListener(v -> {
            retryOnNetError();
        });
    }

    protected void retryOnNetError() {
        //override by subclass
    }

    @Override
    public void showLoading() {
        checkStateHelper();
        mIStateHelper.showLoading();
    }

    @Override
    public void showContent() {
        checkStateHelper();
        mIStateHelper.showContent();
    }

    @Override
    public void showEmpty() {
        checkStateHelper();
        mIStateHelper.showEmpty();
    }

    @Override
    public void showError() {
        checkStateHelper();
        mIStateHelper.showError();
    }

    @Override
    public ViewAnimator getStateViewAnimator() {
        if (mIStateHelper != null) {
            return mIStateHelper.getStateViewAnimator();
        }

        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIStateHelper = null;
    }

    private void checkStateHelper() {
        if (mIStateHelper == null) {
            throw new IllegalStateException("didn't init state helper");
        }
    }
}
