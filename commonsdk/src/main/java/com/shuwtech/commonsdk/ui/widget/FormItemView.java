package com.shuwtech.commonsdk.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuwtech.commonsdk.R;

/**
 * 普通itemview，支持左侧标题和右侧文字配置
 * */
public class FormItemView extends RelativeLayout {

    private TextView tvDesc;
    private TextView tvValue;

    private String formDesc;
    private String formValue;

    public FormItemView(@NonNull Context context) {
        this(context, null);
    }

    public FormItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FormItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.common_form_item_view, this);
        tvDesc = findViewById(R.id.tvDesc);
        tvValue = findViewById(R.id.tvContent);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FormItemView);
        setFormDesc(a.getString(R.styleable.FormItemView_formDesc));
        if (a.getBoolean(R.styleable.FormItemView_descBold, false)) {
            tvDesc.setTypeface(Typeface.DEFAULT_BOLD);
        }
        tvDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(R.styleable.FormItemView_descSize, 16));
        tvDesc.setTextColor(a.getColor(R.styleable.FormItemView_descColor, Color.DKGRAY));
        tvDesc.setPadding(a.getDimensionPixelSize(R.styleable.FormItemView_descPaddingLeft, 0), 0, 0, 0);

        setFormValue(a.getString(R.styleable.FormItemView_formValue));
        if (a.getBoolean(R.styleable.FormItemView_valueBold, false)) {
            tvValue.setTypeface(Typeface.DEFAULT_BOLD);
        }
        tvValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(R.styleable.FormItemView_valueSize, 16));
        tvValue.setTextColor(a.getColor(R.styleable.FormItemView_valueColor, Color.DKGRAY));
        tvValue.setPadding(0, 0, a.getDimensionPixelSize(R.styleable.FormItemView_valuePaddingRight, 0), 0);

        int divider = a.getResourceId(R.styleable.FormItemView_divider, 0);
        if (divider != 0) {
            View.inflate(context, divider, this);
        }
        a.recycle();
    }

    public String getFormDesc() {
        return formDesc;
    }

    public void setFormDesc(String formDesc) {
        this.formDesc = formDesc;
        tvDesc.setText(formDesc);
    }

    public String getFormValue() {
        return formValue;
    }

    public void setFormValue(String formValue) {
        this.formValue = formValue;
        tvValue.setText(formValue);
    }
}
