package com.shuwtech.commonsdk.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuwtech.commonsdk.R;

public class CommonAlertDialog extends CommonBaseDialog {

    //dialog title
    private TextView mTvTitle;
    //gialog msg
    private TextView mTvMsg;
    //nature 优先级高
    private Button mBtnNature;
    //positive button
    private Button mBtnPos;
    //negative button
    private Button mBtnNeg;
    private LinearLayout mBtnsContainer;

    public CommonAlertDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CommonAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public CommonAlertDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        setContentView(R.layout.common_alert_dialog);
        mTvTitle = findViewById(R.id.tvTitle);
        mTvMsg = findViewById(R.id.tvMsg);
        mBtnNature = findViewById(R.id.btnNature);
        mBtnPos = findViewById(R.id.btnPos);
        mBtnNeg = findViewById(R.id.btnNeg);
        mBtnsContainer = findViewById(R.id.btnsContainer);
    }

    public void setTitle(String title) {
       mTvTitle.setText(title);
    }

    public void setTitle(@StringRes int titleRes) {
        mTvTitle.setText(titleRes);
    }

    public void setMessage(CharSequence message) {
       mTvMsg.setText(message);
    }

    public void setMessage(@StringRes int messageRes) {
        mTvMsg.setText(messageRes);
    }

    public static class Builder {
        private Context mContext;
        private AlertParams mParams;

        public Builder(Context context) {
            mContext = context;
            mParams = new AlertParams();
        }

        public Builder setTitle(String title) {
            mParams.mTitle = title;
            return this;
        }

        public Builder setTitle(@StringRes int titleRes) {
            mParams.mTitle = mContext.getString(titleRes);
            return this;
        }

        public Builder setMessage(CharSequence message) {
            mParams.mMsg = message;
            return this;
        }

        public Builder setMessage(@StringRes int messageRes) {
            mParams.mMsg = mContext.getString(messageRes);
            return this;
        }

        public Builder setPositiveButton(String posText,
                                         View.OnClickListener onPositiveClickListener) {
            mParams.mPosBtnText = posText;
            mParams.mOnPosClickListener = onPositiveClickListener;
            return this;
        }

        public Builder setPositiveButton(@StringRes int posTextRes,
                                         View.OnClickListener onPositiveClickListener) {
            mParams.mPosBtnText = mContext.getString(posTextRes);
            mParams.mOnPosClickListener = onPositiveClickListener;
            return this;
        }

        public Builder setPositivtTextColor(@ColorRes int posTextColor) {
            mParams.mPosTextColor = posTextColor;
            return this;
        }

        public Builder setNegativeButton(String negText,
                                         View.OnClickListener onNegativeClickListener) {
            mParams.mNegBtnText = negText;
            mParams.mOnNegClickListener = onNegativeClickListener;
            return this;
        }

        public Builder setNegativeButton(@StringRes int negTextRes,
                                         View.OnClickListener onNegativeClickListener) {
            mParams.mNegBtnText = mContext.getString(negTextRes);
            mParams.mOnNegClickListener = onNegativeClickListener;
            return this;
        }

        public Builder setNegativeTextColor(@ColorRes int negTextColor) {
            mParams.mNegTextColor = negTextColor;
            return this;
        }

        public Builder setNatureButton(String natureText,
                                       View.OnClickListener onNatureClickListener) {
            mParams.mNatureText = natureText;
            mParams.mOnNatureClickListener = onNatureClickListener;
            return this;
        }

        public Builder setNatureButton(@StringRes int natureTextRes,
                                       View.OnClickListener onNatureClickListener) {
            mParams.mNatureText = mContext.getString(natureTextRes);
            mParams.mOnNatureClickListener = onNatureClickListener;
            return this;
        }

        public Builder setnatureTextColor(@ColorRes int natureTextColor) {
            mParams.mNatureTextColor = natureTextColor;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mParams.mCancelable = cancelable;
            return this;
        }

        public Builder resetWidth(boolean reset) {
            mParams.resetWidth = reset;
            return this;
        }

        public Builder setMessageGravity(int gravity) {
            mParams.messageGravity = gravity;
            return this;
        }

        public CommonAlertDialog create() {
            CommonAlertDialog dialog = new CommonAlertDialog(mContext);
            mParams.apply(dialog);
            return dialog;
        }

        public void show() {
            CommonAlertDialog dialog = create();
            dialog.show();
        }
    }

    private static class AlertParams {
        String mTitle;
        CharSequence mMsg;
        String mPosBtnText;
        String mNegBtnText;
        String mNatureText;
        @ColorRes
        int mPosTextColor;
        @ColorRes
        int mNegTextColor;
        @ColorRes
        int mNatureTextColor;
        boolean resetWidth;
        int messageGravity;
        View.OnClickListener mOnPosClickListener;
        View.OnClickListener mOnNegClickListener;
        View.OnClickListener mOnNatureClickListener;
        boolean mCancelable;

        public AlertParams() {
            mCancelable = true;
            resetWidth = true;
            messageGravity = Gravity.CENTER;
        }

        void apply(final CommonAlertDialog dialog) {
            if (dialog == null) return;

            if (resetWidth) dialog.setWindowStyle(dialog.getContext());

            if (!TextUtils.isEmpty(mTitle)) {
                dialog.mTvTitle.setText(mTitle);
            } else {
                dialog.mTvTitle.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(mMsg)) {
                dialog.mTvMsg.setText(mMsg);
                dialog.mTvMsg.setGravity(messageGravity);
            }

            dialog.setCancelable(mCancelable);
            if (!TextUtils.isEmpty(mNatureText)) {
                dialog.mBtnNature.setVisibility(View.VISIBLE);
                dialog.mBtnsContainer.setVisibility(View.GONE);
                dialog.mBtnNature.setText(mNatureText);
                if (mNatureTextColor != 0) {
                    dialog.mBtnNature.setTextColor(dialog.getContext().getResources()
                        .getColor(mNatureTextColor));
                }
                dialog.mBtnNature.setOnClickListener(v -> {
                    if (mOnNatureClickListener != null) {
                        mOnNatureClickListener.onClick(v);
                    }
                    if (mCancelable) {
                        dialog.dismiss();
                    }
                });
                return;
            } else {
                dialog.mBtnNature.setVisibility(View.GONE);
                dialog.mBtnsContainer.setVisibility(View.VISIBLE);
            }

            if (!TextUtils.isEmpty(mPosBtnText)) {
                dialog.mBtnPos.setText(mPosBtnText);
                if (mPosTextColor != 0) {
                    dialog.mBtnPos.setTextColor(dialog.getContext().getResources()
                        .getColor(mPosTextColor));
                }
                dialog.mBtnPos.setOnClickListener(v -> {
                    if (mOnPosClickListener != null) {
                        mOnPosClickListener.onClick(v);
                    }
                    if (mCancelable) {
                        dialog.dismiss();
                    }
                });
            }

            if (!TextUtils.isEmpty(mNegBtnText)) {
                dialog.mBtnNeg.setText(mNegBtnText);
                if (mNegTextColor != 0) {
                    dialog.mBtnNeg.setTextColor(dialog.getContext().getResources()
                        .getColor(mNegTextColor));
                }
                dialog.mBtnNeg.setOnClickListener(v -> {
                    if (mOnNegClickListener != null) {
                        mOnNegClickListener.onClick(v);
                    }
                    if (mCancelable) {
                        dialog.dismiss();
                    }
                });
            }
        }
    }
}
