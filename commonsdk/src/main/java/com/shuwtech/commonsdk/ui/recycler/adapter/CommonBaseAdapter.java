package com.shuwtech.commonsdk.ui.recycler.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * adapter基类
 * */
public abstract class CommonBaseAdapter<T> extends BaseQuickAdapter<T,DataBindingVH> {

    public CommonBaseAdapter(int layoutResId) {
        super(layoutResId, null);
    }

    @Override
    protected DataBindingVH createBaseViewHolder(View view) {
        return super.createBaseViewHolder(view);
    }
}
