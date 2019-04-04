package com.shuwtech.commonsdk.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.shuwtech.commonsdk.R;
import com.shuwtech.commonsdk.utils.PixelUtils;

import java.lang.reflect.Field;

/**
 * 自定义picker，修改字体，分割线
 * */
public class CommonPicker extends NumberPicker {

    public CommonPicker(Context context) {
        super(context);
        init();
    }

    public CommonPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        try {
            Field dividerHeightField = getClass().getSuperclass().getDeclaredField("mSelectionDividerHeight");
            dividerHeightField.setAccessible(true);
            dividerHeightField.set(this, PixelUtils.dip2px(getContext(), 1f));
            Field dividerFiled = getClass().getSuperclass().getDeclaredField("mSelectionDivider");
            dividerFiled.setAccessible(true);
            dividerFiled.set(this, getResources().getDrawable(R.drawable.common_divider_picker));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    public void updateView(View view) {
        if (view instanceof EditText) {
            ((EditText) view).setInputType(EditorInfo.TYPE_NULL);
            ((EditText) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
    }
}