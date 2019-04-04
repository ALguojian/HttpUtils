package com.shuwtech.commonsdk.ui;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.shuwtech.commonsdk.R;
import com.shuwtech.commonsdk.glide.GlideUtils;
import com.shuwtech.commonsdk.ui.state.StateViewAnimator;
import com.shuwtech.commonsdk.ui.state.statehelper.ContentStateHelper;
import com.shuwtech.commonsdk.ui.state.statehelper.IStateHelper;

/**
 * 支持状态切换
 * */
public class StateActivity extends RxActivity implements IStateHelper {

    private IStateHelper mIStateHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setUpStateHelper(null);
    }

    protected void setUpStateHelper(IStateHelper IStateHelper) {
        //如果自定义statehelper,默认生成statehelper
        if (IStateHelper == null) {
            setUpDefaultStateHelper();
        } else {
            mIStateHelper = IStateHelper;
        }

        setStateLoadImage();
        setStateEmptyImage();
        setNetErrorClickEvent();
    }

    protected void setUpDefaultStateHelper() {
        if (mIStateHelper != null) return;

        View contentLoader = findViewById(R.id.contentLoader);
        if (contentLoader != null
            && contentLoader instanceof StateViewAnimator) {
            mIStateHelper = new ContentStateHelper((StateViewAnimator) contentLoader);
            return;
        }

        View stateContent = findViewById(R.id.stateContent);
        if (stateContent != null) {
            mIStateHelper = new ContentStateHelper(this);
            ((ContentStateHelper) mIStateHelper).setContentView(stateContent);
        }
    }

    //设置loading图标
    protected void setStateLoadImage() {
        if (getStateViewAnimator() == null) return;

        ImageView imgLoading = mIStateHelper.getStateViewAnimator().findViewById(R.id.imgStateLoading);
        if (imgLoading != null) {
            GlideUtils.displayGif(this, R.drawable.common_ic_loading, imgLoading);
        }
    }

    //设置空页面图标
    protected void setStateEmptyImage() {
        if (getStateViewAnimator() == null) return;

        ImageView imgEmpty = getStateViewAnimator().findViewById(R.id.imgStateEmpty);
        GlideUtils.display(this, R.drawable.common_ic_state_empty, imgEmpty);
    }

    //设置空页面图标
    protected void setStateEmptyImage(@DrawableRes int stateEmptyImage) {
        if (getStateViewAnimator() == null) return;

        ImageView imgEmpty = getStateViewAnimator().findViewById(R.id.imgStateEmpty);
        GlideUtils.display(this, stateEmptyImage, imgEmpty);
    }

    //设置空页面文字
    protected void setStateEmptyText(@StringRes int stateEmptyTextRes) {
        if (getStateViewAnimator() == null) return;
        TextView tvEmpty = getStateViewAnimator().findViewById(R.id.tvStateEmpty);
        tvEmpty.setText(stateEmptyTextRes);
    }

    //设置空页面文字
    protected void setStateEmptyText(String stateEmptyText) {
        if (getStateViewAnimator() == null) return;
        TextView tvEmpty = getStateViewAnimator().findViewById(R.id.tvStateEmpty);
        tvEmpty.setText(stateEmptyText);
    }

    //设置网络错误界面点击事件
    private void setNetErrorClickEvent() {
        if(getStateViewAnimator() == null) return;
        View view = getStateViewAnimator().findViewById(R.id.llError);
        if(view == null) return;
        view.setOnClickListener(v -> retryOnNetError());
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
        if (mIStateHelper != null) return mIStateHelper.getStateViewAnimator();
        return null;
    }

    private void checkStateHelper() {
        if (mIStateHelper == null) {
            throw new IllegalStateException("didn't init state helper");
        }
    }
}
