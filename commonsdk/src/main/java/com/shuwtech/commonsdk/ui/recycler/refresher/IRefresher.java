package com.shuwtech.commonsdk.ui.recycler.refresher;

import android.view.View;

/**
 * refresh 接口
 * */
public abstract class IRefresher {

    protected RefreshListener refreshListener;

    //停止刷新
    public abstract void stopRefresh();

    //设置是否可以刷新
    public abstract void setRefreshEnable(boolean enable);

    public abstract boolean isRefreshing();

    public abstract boolean isRefreshEnable();

    public void setRefreshListener(RefreshListener listener) {
        refreshListener = listener;
    }

    public abstract <V extends View> V getRefreshView();
}
