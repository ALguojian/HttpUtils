package com.shuwtech.commonsdk.ui.recycler.recyclerhelper;

import com.shuwtech.commonsdk.http.response.BaseResponse;
import com.shuwtech.commonsdk.ui.recycler.adapter.CommonBaseAdapter;

import java.util.List;

import io.reactivex.Flowable;

/**
 * recycler接口，获取列表接口及adapter
 * */
public interface IRecycler<T> {

    Flowable<BaseResponse<List<T>>> request(int pageNum, int pageSize);

    CommonBaseAdapter getAdapter();
}
