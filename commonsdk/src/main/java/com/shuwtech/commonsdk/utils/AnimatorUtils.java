package com.shuwtech.commonsdk.utils;

import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class AnimatorUtils {

    private static final int SCROLL_BAR_MAX_PADDING_TOP = 36;
    private static final int SCROLL_BAR_MIN_PADDING_TOP = 12;
    private static final int SCROLL_BAR_MAX_TITLE_SIZE = 24;
    private static final int SCROLL_BAR_MIN_TITLE_SIZE = 18;

    /**
     * tab页toolbar滚动padding top
     */
    public static void setTabScrollbar(View scrollbar, TextView titleView, float fraction) {
        int paddingTop = (int) (SCROLL_BAR_MAX_PADDING_TOP + fraction * (SCROLL_BAR_MIN_PADDING_TOP - SCROLL_BAR_MAX_PADDING_TOP));
        float textSize = SCROLL_BAR_MAX_TITLE_SIZE + fraction * (SCROLL_BAR_MIN_TITLE_SIZE - SCROLL_BAR_MAX_TITLE_SIZE);

        titleView.post(() -> {
            scrollbar.setPadding(scrollbar.getPaddingLeft(), PixelUtils.dip2px(scrollbar.getContext(), paddingTop),
                scrollbar.getPaddingRight(), scrollbar.getPaddingBottom());
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        });
    }
}
