package com.shuwtech.commonsdk.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shuwtech.commonsdk.R;

public abstract class CommonBottomAlertDialog extends BottomSheetDialog {
    protected Button btnCancel;
    protected Button btnComplete;
    protected FrameLayout content;
    protected TextView tvDialogTitle;

    private OnCompleteListener mOnCompleteListener;

    public CommonBottomAlertDialog(@NonNull Context context) {
        super(context);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.common_bottom_alert_dialog);
        btnCancel = findViewById(R.id.btnCancel);
        btnComplete = findViewById(R.id.btnComplete);
        content = findViewById(R.id.content);
        tvDialogTitle = findViewById(R.id.tvDialogTitle);

        if (setContent() != null) {
            content.removeAllViews();
            content.addView(setContent(), new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
        }

        btnCancel.setOnClickListener(v -> dismiss());
        btnComplete.setOnClickListener(v -> {
            if(mOnCompleteListener != null) mOnCompleteListener.onComplete();
        });
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        mOnCompleteListener = onCompleteListener;
    }

    protected abstract View setContent();


    public interface OnCompleteListener {
        void onComplete();
    }
}
