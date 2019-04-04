package com.shuwtech.commonsdk.ui;

import android.support.v7.app.AppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 管理disposable
 * */
public class RxActivity extends AppCompatActivity {

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
    protected void onDestroy() {
        super.onDestroy();
        cleanDisposables();
    }
}
