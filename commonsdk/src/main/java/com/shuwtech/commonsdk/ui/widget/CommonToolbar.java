package com.shuwtech.commonsdk.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shuwtech.commonsdk.R;
import com.shuwtech.commonsdk.utils.PixelUtils;

/**
 * 自定义toolbar
 * */
public class CommonToolbar extends FrameLayout {

    //home up icon
    private ImageButton ivHomeUp;
    //页面标题
    private TextView tvTitle;
    //menu icon ，和menu文字不共存
    private ImageButton ivMenu;
    //menu 文字
    private TextView tvMenu;

    //是否使用home up
    protected boolean homeUpEnable;
    protected CharSequence title;

    public CommonToolbar(Context context) {
        this(context, null);
    }

    public CommonToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.common_toolbar, this);
        ivHomeUp = findViewById(R.id.ivHomeUp);
        tvTitle = findViewById(R.id.tvCommonTitle);
        ivMenu = findViewById(R.id.ivMenu);
        tvMenu = findViewById(R.id.tvMenu);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommonToolbar);
        ivHomeUp.setImageResource(a.getResourceId(R.styleable.CommonToolbar_homeUp,
            R.drawable.common_ic_home_up));
        setHomeUpEnable(a.getBoolean(R.styleable.CommonToolbar_homeUpEnable, true));
        setTitle(a.getString(R.styleable.CommonToolbar_title));
        setIvMenu(a.getDrawable(R.styleable.CommonToolbar_ivMenu));
        setMenuText(a.getString(R.styleable.CommonToolbar_tvMenu));
        setMenuTextColor(a.getColor(R.styleable.CommonToolbar_tvMenuColor,
            getResources().getColor(R.color.common_text_color_primary_39364D)));
        setMenuDrawable(a.getDrawable(R.styleable.CommonToolbar_tvMenuDrawable));
        setMenuDrawPadding(a.getDimensionPixelSize(R.styleable.CommonToolbar_tvMenuDrawPadding,
            PixelUtils.dip2px(context, 4)));
        a.recycle();
    }

    public void setHomeUp(Drawable homeUp) {
        ivHomeUp.setImageDrawable(homeUp);
    }

    public void setHomeUp(@DrawableRes int homeUpRes) {
        ivHomeUp.setImageResource(homeUpRes);
    }

    public boolean isHomeUpEnable() {
        return homeUpEnable;
    }

    public void setHomeUpEnable(boolean homeUpEnable) {
        this.homeUpEnable = homeUpEnable;
        ivHomeUp.setVisibility(homeUpEnable ? View.VISIBLE : View.GONE);
    }

    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
        tvTitle.setText(title);
    }

    public void setTitle(@StringRes int titleRes) {
        this.title = getResources().getString(titleRes);
        tvTitle.setText(titleRes);
    }

    public void setIvMenu(Drawable menu) {
        ivMenu.setImageDrawable(menu);
        if (menu == null) ivMenu.setVisibility(GONE);
    }

    public void setIvMenu(@DrawableRes int menuRes) {
        ivMenu.setImageResource(menuRes);
        if (menuRes == 0) ivMenu.setVisibility(GONE);
    }

    public void setMenuText(CharSequence menu) {
        tvMenu.setText(menu);
        if (menu == null || menu.length() == 0) tvMenu.setVisibility(GONE);
    }

    public void setMenuTextColor(int color) {
        tvMenu.setTextColor(color);
    }

    public void setMenuText(@StringRes int menuRes) {
        tvMenu.setText(menuRes);
        if (menuRes == 0) tvMenu.setVisibility(GONE);
    }

    public String getMenuText() {
        return tvMenu.getText().toString();
    }

    public void setMenuDrawPadding(int padding) {
        tvMenu.setCompoundDrawablePadding(padding);
    }

    //设置menu文字左侧图标
    public void setMenuDrawable(Drawable menuDrawable) {
        tvMenu.setCompoundDrawablesWithIntrinsicBounds(null, null, menuDrawable, null);
    }

    //设置menu文字左侧图标
    public void setMenuDrawable(@DrawableRes int menuDrawable) {
        tvMenu.setCompoundDrawablesWithIntrinsicBounds(0, 0, menuDrawable, 0);
    }

    public void setMenuEnable(boolean enable) {
        if (enable) {
            if (ivMenu.getDrawable() != null) {
                ivMenu.setVisibility(VISIBLE);
            } else if (tvMenu.getText() != null) {
                tvMenu.setVisibility(VISIBLE);
            }
        } else {
            ivMenu.setVisibility(GONE);
            tvMenu.setVisibility(GONE);
        }
    }

    public void setHomeUpClickListener(View.OnClickListener onClickListener) {
        if (onClickListener != null) ivHomeUp.setOnClickListener(onClickListener);
    }

    public void setMenuClickListener(View.OnClickListener onClickListener) {
        if (onClickListener != null) {
            ivMenu.setOnClickListener(onClickListener);
            tvMenu.setOnClickListener(onClickListener);
        }
    }
}
