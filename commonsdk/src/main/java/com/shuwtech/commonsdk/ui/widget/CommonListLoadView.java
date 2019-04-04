package com.shuwtech.commonsdk.ui.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.shuwtech.commonsdk.R;

/**
 * 列表loadmoreview
 * */
public class CommonListLoadView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.common_list_load_view;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.common_load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.common_load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.common_load_more_load_end_view;
    }
}