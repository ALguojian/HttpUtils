package com.shuwtech.commonsdk.ui.widget;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shuwtech.commonsdk.glide.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/***
 * 引导view
 * */
public class GuideLayout extends FrameLayout implements View.OnClickListener {
    private final ImageView ivText;
    private final ImageView ivIcon;
    private final Activity activity;
    private List<GuideParam> guideParams;
    private boolean oneByOne;
    private int index;
    private TargetViewClickListener targetViewClickListener;
    private ObjectAnimator animator;
    private boolean isShowing;
    private ViewGroup viewGroup;
    private Paint paint;
    private GuideParam guideParam;
    private Rect rect;

    public GuideLayout(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0x60000000);

        ivText = new ImageView(getContext());
        ivIcon = new ImageView(getContext());

        setOnClickListener(this);
        ivText.setOnClickListener(this);
        ivIcon.setOnClickListener(this);

        addView(ivText);
        addView(ivIcon);
        setWillNotDraw(false);
    }

    /**
     * 单个引导
     *
     * @param guideParam ：引导参数
     */
    public GuideLayout guide(GuideParam guideParam) {
        List<GuideParam> guideParams = new ArrayList<>();
        if (guideParam != null) {
            guideParams.add(guideParam);
        }
        return guideList(guideParams);
    }

    /**
     * 多个引导
     *
     * @param guideParams ：引导参数集合
     * @return
     */
    public GuideLayout guideList(List<GuideParam> guideParams) {
        this.guideParams = guideParams;
        return this;
    }

    /**
     * 是否一个接一个引导（点击空白位置或者文案）
     */
    public GuideLayout oneByOne(boolean oneByOne) {
        this.oneByOne = oneByOne;
        return this;
    }

    /**
     * 引导控件点击监听
     */
    public GuideLayout indexClick(TargetViewClickListener targetViewClickListener) {
        this.targetViewClickListener = targetViewClickListener;
        return this;
    }

    /**
     * 准备开始引导
     */
    public GuideLayout build() {
        startGuide(0);
        return this;
    }

    /**
     * 开始引导第index个引导
     *
     * @param index
     */
    private void startGuide(int index) {
        this.index = index;
        ivText.clearAnimation();
        ivText.setOnClickListener(null);
        ivIcon.setOnClickListener(null);
        if (animator != null) {
            animator.cancel();
            animator = null;
        }

        if (cantGuideNext(index) || guideParams.get(index) == null) {
            if (targetViewClickListener != null) {
                targetViewClickListener.oneByOneEnd();
            }
            dismiss();
            return;
        }

        final GuideParam guideParam = guideParams.get(index);
        final View targetView = guideParam.targetView;
        if (targetView.getMeasuredWidth() > 0 && targetView.getMeasuredHeight() > 0) {
            startGuide(guideParam, targetView);
            return;
        }
        ViewTreeObserver observer = targetView.getViewTreeObserver();
        if (!observer.isAlive()) {
            dismiss();
            return;
        }
        // 监听控件是否layout完成
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (targetView.getMeasuredWidth() <= 0 || targetView.getMeasuredHeight() <= 0) {
                    return;
                }
                ViewTreeObserver treeObserver = targetView.getViewTreeObserver();
                if (treeObserver.isAlive()) {
                    treeObserver.removeOnGlobalLayoutListener(this);
                }
                startGuide(guideParam, targetView);
            }
        });
    }

    /**
     * 开始布置引导
     */
    private void startGuide(GuideParam guideParam, View targetView) {
        this.guideParam = guideParam;
        ivText.setOnClickListener(GuideLayout.this);
        ivIcon.setOnClickListener(GuideLayout.this);

        int[] locations = new int[2];
        targetView.getLocationInWindow(locations);

        rect = new Rect(locations[0] - guideParam.guideOffWidth / 2 + guideParam.guideOffX
                , locations[1] - guideParam.guideOffHeight / 2 + guideParam.guideOffY
                , locations[0] + targetView.getMeasuredWidth() + guideParam.guideOffWidth / 2 + guideParam.guideOffX
                , locations[1] + targetView.getMeasuredHeight() + guideParam.guideOffHeight / 2 + guideParam.guideOffY);

        Log.e("GUIDE", "startGuide: " + rect);

        LayoutParams iconParams = new LayoutParams(rect.right - rect.left, rect.bottom - rect.top);
        iconParams.topMargin = rect.top;
        iconParams.leftMargin = rect.left;
        ivIcon.setLayoutParams(iconParams);

        int dp53 = (int) (getContext().getResources().getDisplayMetrics().density * 53);
        int dp5 = (int) (getContext().getResources().getDisplayMetrics().density * 5);

        LayoutParams textParams = new LayoutParams(guideParam.textWidth, guideParam.textHeight <= 0 ? dp53 : guideParam.textHeight);
        textParams.leftMargin = rect.left + guideParam.horMargin + guideParam.guideOffWidth / 2;
        textParams.topMargin = rect.top + guideParam.verMargin + guideParam.guideOffHeight / 2;

        ivText.setLayoutParams(textParams);
        GlideUtils.display(getContext(), guideParam.textIcon, ivText);

        animator = ObjectAnimator.ofFloat(ivText, guideParam.animIsVer ? "translationY" : "translationX", 0, dp5, -dp5, 0);
        animator.setDuration(1000);
        animator.setRepeatCount(-1);
        animator.start();
        invalidate();
    }

    /**
     * 判断是否不需要引导
     */
    private boolean cantGuideNext(int index) {
        return guideParamsIsEmpty() || index < 0 || index >= guideParams.size();
    }

    /**
     * 引导参数集合是否为空
     */
    private boolean guideParamsIsEmpty() {
        return guideParams == null || guideParams.isEmpty();
    }

    @Override
    public void onClick(View v) {
        if (v == ivIcon) {
            // 引导控件点击监听
            if (targetViewClickListener != null){
                targetViewClickListener.targetClick(guideParams.get(index).targetView.getId());
            }
            if (guideParamsIsEmpty() || index <= 0 || index >= guideParams.size()) {
                dismiss();
            }

        } else {
            // 如果点击其他空白地方不做任何操作
            if (v != ivText) {
                return;
            }
            // 点击文案或者空白页
            if (oneByOne) {
                startGuide(index + 1);
            } else {
                dismiss();
            }
        }
    }

    /**
     * 隐藏引导
     */
    public void dismiss() {
        if(!isShowing) return;

        if(targetViewClickListener != null) {
            targetViewClickListener.onDismiss(guideParam);
        }
        isShowing = false;
        ivText.setVisibility(View.GONE);
        ivIcon.setVisibility(View.GONE);
        if (guideParams != null) {
            guideParams.clear();
            guideParams = null;
            index = 0;
        }
        if (viewGroup != null) {
            viewGroup.removeView(this);
        }
    }

    /**
     * 以activity.getWindow().getDecorView()为父控件展示引导
     */
    public void show() {
        if (activity != null) {
            show((FrameLayout) activity.getWindow().getDecorView());
        }
    }

    /**
     * 以viewGroup为父控件展示引导
     */
    public void show(ViewGroup viewGroup) {
        if (viewGroup != null) {
            isShowing = true;
            this.viewGroup = viewGroup;
            this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            viewGroup.addView(this);
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGuide(canvas);
    }

    /**
     * 绘制引导图层和挖洞
     */
    private void drawGuide(Canvas canvas) {
        if (guideParam == null || rect == null || canvas == null)
            return;
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        //绘制背景
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawRect(0, 0, canvasWidth, canvasHeight, paint);

        if (GuideParam.State.CIRCLE == guideParam.state)
            drawCircle(canvas, rect);
        else if (GuideParam.State.ROUND_RECT == guideParam.state)
            drawRoundRect(canvas, rect);
        else if (GuideParam.State.RECT == guideParam.state)
            drawRect(canvas, rect);
        canvas.restoreToCount(layerId);
    }

    /**
     * 挖圆形洞
     *
     * @param rect ： targetView位置信息
     */
    private void drawCircle(Canvas canvas, Rect rect) {
        //挖洞
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        //绘制圆形，算出外接圆的半径
        canvas.drawCircle((rect.left + rect.right) / 2
                , (rect.top + rect.bottom) / 2
                , (int) Math.sqrt((rect.right - rect.left) * (rect.right - rect.left) + (rect.bottom - rect.top) * (rect.bottom - rect.top)) / 2
                , paint);
        paint.setXfermode(null);
    }

    /**
     * 挖圆角矩形矩形洞
     *
     * @param rect ： targetView位置信息
     */
    private void drawRoundRect(Canvas canvas, Rect rect) {
        //挖洞
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        RectF rectF = new RectF(rect.left, rect.top, rect.right, rect.bottom);

        canvas.drawRoundRect(rectF, (rectF.bottom - rectF.top)/10, (rectF.bottom - rectF.top)/10, paint);
        paint.setXfermode(null);
    }

    /**
     * 挖矩形洞
     *
     * @param rect ： targetView位置信息
     */
    private void drawRect(Canvas canvas, Rect rect) {
        //挖洞
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRect(rect, paint);
        paint.setXfermode(null);
    }

    public interface TargetViewClickListener {
        void targetClick(int viewId);

        void oneByOneEnd();

        void onDismiss(GuideParam guideParam);
    }

    public static class GuideParam {
        private final float density;
        /**
         * 需要引导的控件
         */
        public final View targetView;
        /**
         * 引导控件的文案图片
         */
        @DrawableRes
        public int textIcon;
        /**
         * 文案控件相对引导控件左边的间距
         */
        public int horMargin;
        /**
         * 文案控件相对引导控件上边的间距
         */
        public int verMargin;
        /**
         * 文案控件的高度
         */
        public int textHeight;
        /**
         * 文案控件的宽度
         */
        public int textWidth;
        /**
         * 挖洞类型
         */
        public State state;

        /**
         * 挖的洞的宽度偏移量
         */
        public int guideOffWidth;

        /**
         * 挖的洞的高度偏移量
         */
        public int guideOffHeight;

        /**
         * 挖的洞的X轴偏移量
         */
        public int guideOffX;

        /**
         * 挖的洞的Y轴偏移量
         */
        public int guideOffY;

        /**是否上下动画*/
        public boolean animIsVer;

        public GuideParam(View targetView, int textIcon, int horMargin, int verMargin) {
            this(targetView, textIcon, horMargin, verMargin, State.CIRCLE);
        }

        public GuideParam(View targetView, int textIcon, int horMargin, int verMargin, State state) {
            this(targetView, textIcon, horMargin, verMargin, state, 0, 0);
        }

        public GuideParam(View targetView, int textIcon, int horMargin, int verMargin, int guideOffWidth, int guideOffHeight) {
            this(targetView, textIcon, horMargin, verMargin, State.CIRCLE, guideOffWidth, guideOffHeight);
        }

        public GuideParam(View targetView, int textIcon, int horMargin, int verMargin, State state, int guideOffWidth, int guideOffHeight) {
            density = targetView.getContext().getResources().getDisplayMetrics().density;
            this.targetView = targetView;
            this.textIcon = textIcon;
            this.horMargin = (int) (horMargin * density);
            this.verMargin = (int) (verMargin * density);
            this.state = state;
            this.guideOffWidth = (int) (guideOffWidth * density);
            this.guideOffHeight = (int) (guideOffHeight * density);
        }

        public void setAnimIsVer(boolean animIsVer) {
            this.animIsVer = animIsVer;
        }

        public void setTextWidthHeight(int width, int height) {
            this.textWidth = (int) (density * width);
            this.textHeight = (int) (density * height);
        }

        public void setOffGuideOff(int guideOffX, int guideOffY) {
            this.guideOffX = (int) (guideOffX * density);
            this.guideOffY = (int) (guideOffY * density);
        }

        public enum State {
            /**
             * 圆形
             */
            CIRCLE,
            /**
             * 圆角矩形
             */
            ROUND_RECT,

            RECT
        }
    }
}