package com.example.kyapplication.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.kyapplication.R;
import com.example.kyapplication.utils.F;


public class CircleView extends View {
    private final Paint innerPaint, outPaint;
    private RectF innerRectF, outRectF;
    private final float innerCircleWidth, outCircleWidth;
    private int outProgress, innerProgress;


    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        outCircleWidth = typedArray.getDimension(R.styleable.CircleView_outWidth, 22);
        innerCircleWidth = typedArray.getDimension(R.styleable.CircleView_innerWidth, 22);

        outPaint = new Paint();
        outPaint.setColor(typedArray.getColor(R.styleable.CircleView_outColor, Color.GREEN));
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setStrokeWidth(outCircleWidth);
        outPaint.setAntiAlias(true);
        outProgress = typedArray.getInteger(R.styleable.CircleView_outProgress, 90);

        innerPaint = new Paint();
        innerPaint.setColor(typedArray.getColor(R.styleable.CircleView_innerColor, Color.BLUE));
        innerPaint.setStyle(Paint.Style.STROKE);
        innerPaint.setStrokeWidth(innerCircleWidth);
        innerPaint.setAntiAlias(true);
        innerProgress = typedArray.getInteger(R.styleable.CircleView_innerProgress, 90);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
        int viewWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int viewHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int rectLength = (int) (((Math.min(viewHeight, viewWidth)) - outCircleWidth));
        F.d("viewWidth " + viewWidth);
        F.d("rectLength " + rectLength);
        int outLeft = getPaddingLeft() + (viewWidth - rectLength) / 2;
        int outTop = getPaddingTop() + (viewHeight - rectLength) / 2;
        int outRight = outLeft + rectLength;
        int outBottom = outTop + rectLength;
        F.d(outCircleWidth + "");
        F.d(outLeft + " " + outTop + " " + outRight + " " + outBottom);  //
        if (outRectF == null)
            outRectF = new RectF(outLeft, outTop, outRight, outBottom);
        float innerLeft = outLeft + outPaint.getStrokeWidth() / 2 + innerPaint.getStrokeWidth() / 2;
        float innerTop = outTop + outPaint.getStrokeWidth() / 2 + innerPaint.getStrokeWidth() / 2;
        float innerRight = outRight - outPaint.getStrokeWidth() / 2 - innerPaint.getStrokeWidth() / 2;
        float innerBottom = outBottom - outPaint.getStrokeWidth() / 2 - innerPaint.getStrokeWidth() / 2;
        F.d(innerLeft + " " + innerTop + " " + innerRight + " " + innerBottom);
        if (innerRectF == null)
            innerRectF = new RectF(innerLeft, innerTop, innerRight, innerBottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(outRectF, -90f, outProgress, false, outPaint);
        canvas.drawArc(innerRectF, -90f, innerProgress, false, innerPaint);

    }

    /**
     * 设置外圆环颜色
     *
     * @param color 圆环颜色
     */
    public void setOutAnnulusColor(@ColorRes int color) {
        outPaint.setColor(ContextCompat.getColor(getContext(), color));
        outPaint.setShader(null);
        invalidate();
    }

    public void setOutAnnulusColorInt(@ColorInt int color) {
        outPaint.setColor(color);
        outPaint.setShader(null);
        invalidate();
    }

    /**
     * 设置内圆环颜色(支持渐变色)
     *
     * @param color 圆环颜色
     */
    public void setInnerAnnulusColor(@ColorRes int color) {
        innerPaint.setColor(ContextCompat.getColor(getContext(), color));
        innerPaint.setShader(null);
        invalidate();
    }

    public void setInnerAnnulusColorInt(@ColorInt int color) {
        innerPaint.setColor( color);
        innerPaint.setShader(null);
        invalidate();
    }






//    /**
//     * 设置内圆环颜色(支持渐变色)
//     *
//     * @param color 圆环颜色
//     */
//    public void setInnerAnnulusColorInt(@ColorInt int color) {
//        innerPaint.setColor(ContextCompat.getColor(getContext(), color));
//        innerPaint.setShader(null);
//        invalidate();
//    }

    /**
     * 设置外圆环颜色
     *
     * @param width 圆环宽度
     */
    public void setOutWidth(float width) {
        outPaint.setStrokeWidth(width);
        invalidate();
    }

    /**
     * 设置内圆环颜色(支持渐变色)
     *
     * @param color 圆环颜色
     */
    public void setInnerWidth(@ColorRes int color) {
        innerPaint.setColor(ContextCompat.getColor(getContext(), color));
        innerPaint.setShader(null);
        invalidate();
    }

    /**
     * 设置外圆环角度
     *
     * @param progress 0-360
     */
    public void setOutProgress(int progress) {
        outProgress = progress;
        invalidate();
    }

    /**
     * 设置内圆环角度
     *
     * @param progress 0-360
     */
    public void setInnerProgress(int progress) {
        innerProgress = progress;
        invalidate();
    }


    public void setInnerTime(int allTime, int start, int end) {
//        int allTime = spotTime*num +restTime*(num -1);
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                innerProgress = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
//        animator.setInterpolator(new OvershootInterpolator());
        animator.setDuration(allTime);
        animator.start();
    }


    public void setOutTime(int allTime, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                outProgress = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
//        animator.setInterpolator(new OvershootInterpolator());
        animator.setDuration(allTime);
        animator.start();
    }

}
