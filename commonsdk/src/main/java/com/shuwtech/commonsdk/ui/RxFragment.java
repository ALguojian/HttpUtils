package com.shuwtech.commonsdk.ui;

import android.support.v4.app.Fragment;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 管理disposable
 * */
public class RxFragment extends Fragment {

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    public void compose(Disposable... disposables) {
        if (disposables == null) return;
        for (Disposable disposable : disposables) {
            mDisposables.add(disposable);
        }
    }

    public void cleanDisposables() {
        mDisposables.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cleanDisposables();
    }
}
