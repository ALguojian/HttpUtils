package com.shuwtech.commonsdk.ui.recycler.refresher;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

/**
 * 以SwipeRefreshLayout为刷新view的refresher
 * */
public class SwipeRefresher extends IRefresher implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;

    public SwipeRefresher(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
    }

    @Override
    public void onRefresh() {
        if (refreshListener != null) refreshListener.onRefresh();
    }

    @Override
    public void stopRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setRefreshEnable(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }

    @Override
    public boolean isRefreshing() {
        return swipeRefreshLayout.isRefreshing();
    }

    @Override
    public boolean isRefreshEnable() {
        return swipeRefreshLayout.isEnabled();
    }

    @Override
    public <V extends View> V getRefreshView() {
        return (V) swipeRefreshLayout;
    }
}
