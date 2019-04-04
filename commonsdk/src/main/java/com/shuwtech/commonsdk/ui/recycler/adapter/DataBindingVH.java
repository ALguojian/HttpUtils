package com.shuwtech.commonsdk.ui.recycler.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;

public class DataBindingVH extends BaseViewHolder {

    public DataBindingVH(ViewGroup parent, @LayoutRes int itemLayoutRes) {
        super(LayoutInflater.from(parent.getContext()).inflate(itemLayoutRes, parent, false));
    }

    public DataBindingVH(View itemView) {
        super(itemView);
    }

    public <T extends ViewDataBinding> T getBinding() {
        return DataBindingUtil.bind(itemView);
    }
}
