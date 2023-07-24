package com.example.kyapplication.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.example.kyapplication.R;
import com.example.kyapplication.utils.F;


public class ColorBar extends View {

    private Paint mColorPaint;
    private Paint mSlidePaint;
    int[] colors = new int[]{Color.BLUE, Color.RED, Color.GREEN};
    float[] points = new float[]{0f, 0.5f, 1f};
    private Shader mBarShader;
    private float mCircleRadius = 0f;
    private float mBarHeight = 0f;
    private Paint mStrokePaint;


    public ColorBar(Context context) {
        this(context, null);
    }

    public ColorBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mColorPaint = new Paint();
        mSlidePaint = new Paint();


        mSlidePaint.setAntiAlias(true);
        mSlidePaint.setColor(Color.BLUE);
        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorBar);
        mCircleRadius = typedArray.getDimension(R.styleable.ColorBar_circleRadius, 20f);
        mBarHeight = typedArray.getDimension(R.styleable.ColorBar_barHeight, 10f);


        mStrokePaint = new Paint();
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(1.0f);
        mStrokePaint.setColor(Color.BLACK);
    }
    private RectF mRect ;
    private RectF mCircleRectF;



    @SuppressLint({"DrawAllocation", "WrongConstant"})
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);




        int viewWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
//        int viewHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int viewHeight = getMeasuredHeight();



        if (mBarShader == null) {
            mBarShader = new LinearGradient(0, 0, getMeasuredWidth(), 0, colors, points, Shader.TileMode.CLAMP);
//        LinearGradient linearGradient = new LinearGradient(0,0,getMeasuredWidth(),0,colors,position, Shader.TileMode.CLAMP);
            mColorPaint.setShader(mBarShader);
        }
        if (mBarHeight < 5)
            mBarHeight = 5f;

        if (mCircleRadius < 5)
            mCircleRadius = 5f;

        if (mBarHeight > mCircleRadius*2)
            mBarHeight = mCircleRadius*2;


        float top = 0;
        float bottom = 0;
        if( getPaddingTop() > getPaddingBottom() )
        {
            top = getPaddingTop()-getPaddingBottom();
            bottom = -top;
        }else {
            bottom = getPaddingBottom()-getPaddingTop();
            top = -bottom;
        }


        float rectLeft = mCircleRadius + getPaddingLeft();
        float rectTop =  top - mBarHeight / 2f +viewHeight/2f;
        float rectRight = getMeasuredWidth() - getPaddingRight() - mCircleRadius;
        float rectBottom =  rectTop+mBarHeight;


        if (viewHeight < mCircleRadius)
        {
            rectTop = mCircleRadius - mBarHeight / 2f +viewHeight/2f;
            rectBottom = mCircleRadius  + mBarHeight / 2f+viewHeight/2f;
        }

        if(mRect==null)
        mRect = new RectF(rectLeft,rectTop,rectRight,rectBottom);


        mBitmap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        GradientDrawable dynamicDrawable = new GradientDrawable();
        dynamicDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        dynamicDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        dynamicDrawable.setUseLevel(false);
        dynamicDrawable.setColors(colors);
        dynamicDrawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
        Canvas canvas = new Canvas(mBitmap);
        dynamicDrawable.draw(canvas);



        minX = (int) mRect.left;
        maxX = (int) (mRect.right );

        mCircleX = mRect.left;
        mCircleY = mRect.top+(mRect.bottom-mRect.top)/2f;
    }
    private Bitmap mBitmap;
    private float minX,maxX;
    private float mCircleX,mCircleY;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap,null,mRect,null);
        canvas.drawCircle(mCircleX,mCircleY,mCircleRadius,mSlidePaint);
        canvas.drawCircle(mCircleX,mCircleY,mCircleRadius,mStrokePaint);

    }

    private float cacheX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventType = event.getAction();
        switch (eventType)
        {
            case MotionEvent.ACTION_DOWN:
                F.d("ACTION_DOWN");

                F.d("getRawX:"+event.getRawX());
                cacheX = event.getRawX()- getLeft();
                mCircleX = event.getRawX() - getLeft();
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                F.d("ACTION_MOVE");

                if((event.getRawX() - cacheX) >5  || (event.getRawX() - cacheX) <-5)
                {
                    mCircleX = event.getRawX() - getLeft();

                    if(mCircleX<minX) {
                        mCircleX = minX;
                    }else if(mCircleX>maxX) {
                        mCircleX = maxX;
                    }

                    if(mBitmap!=null)
                    {
                        int pixel = mBitmap.getPixel((int) mCircleX,mBitmap.getHeight()/2);

                        int c = pixelToColor(pixel);

                        int color = pixelToColor(c);

                        if(onColorChangedListener!=null)
                        {
                            onColorChangedListener.onChanged(color);
                        }
                        mSlidePaint.setColor(color);
                    }

                    invalidate();
                }

                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        return true;
    }

    private int pixelToColor(int pixel) {

        int alpha = Color.alpha(pixel);
        int red = Color.red(pixel);
        int green = Color.green(pixel);
        int blue = Color.blue(pixel);
        return Color.argb(alpha, red, green, blue);
    }

    private  Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private OnColorChangedListener onColorChangedListener;
    public void setOnColorChangedListener(OnColorChangedListener onColorChangedListener)
    {
        this.onColorChangedListener = onColorChangedListener;
    }

    public interface OnColorChangedListener
    {
        void onChanged(@ColorInt int color);
    }


}
