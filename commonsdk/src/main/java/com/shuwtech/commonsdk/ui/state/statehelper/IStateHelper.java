package com.shuwtech.commonsdk.ui.state.statehelper;

import android.widget.ViewAnimator;

/**
 * 页面状态切换接口
 * */
public interface IStateHelper {

    void showLoading();

    void showContent();

    void showEmpty();

    void showError();

    ViewAnimator getStateViewAnimator();
}
