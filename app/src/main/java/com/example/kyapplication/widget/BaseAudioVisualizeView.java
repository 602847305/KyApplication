package com.example.kyapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.kyapplication.R;
import com.example.kyapplication.media.MediaManager;
import com.example.kyapplication.media.MediaManagerListener;
import com.example.kyapplication.visualize.VisualizeCallback;
import com.example.kyapplication.visualize.VisualizerHelper;

public abstract class  BaseAudioVisualizeView extends View implements VisualizeCallback, MediaManagerListener {
    private final  String  TAG = getClass().getName();
    private RectF mRect;
    private Paint mPaint;
    private Path mPath;
    //画笔颜色
    private int mColor;
    private int mSpectrumCount;
    private float mSpectrumRatio;
    private float mItemMargin;
    private float centerX, centerY;
    private MediaManager mMediaManager;
    private VisualizerHelper mVisualizerHelper;
    //宽度
    private int mStrokeWidth;


    public BaseAudioVisualizeView(Context context) {
        this(context,null);
    }

    public BaseAudioVisualizeView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public BaseAudioVisualizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,R.styleable.AudioVisualizeView,defStyleAttr,0);
        mColor = typedArray.getColor(R.styleable.AudioVisualizeView_visualize_color, Color.WHITE);
        mSpectrumCount = typedArray.getInteger(R.styleable.AudioVisualizeView_visualize_count, 60);
        mSpectrumRatio = typedArray.getFloat(R.styleable.AudioVisualizeView_visualize_ratio, 1.0f);
        mItemMargin = typedArray.getDimension(R.styleable.AudioVisualizeView_visualize_item_margin, 12f);
        handleAttr(typedArray);
        init();
    }

    protected void init() {
        mStrokeWidth = 5;

        mPaint = new Paint();
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔末端为圆角
        mPaint.setAntiAlias(true);//设置为抗锯齿
        mPaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.SOLID));//画笔模糊效果

        mRect = new RectF();
        mPath = new Path();

        mMediaManager = new MediaManager(getContext());
        mMediaManager.setMediaManagerListener(this);

        mVisualizerHelper = new VisualizerHelper();
        mVisualizerHelper.setVisualizeCallback(this);
        mVisualizerHelper.setVisualCount(mSpectrumCount);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawChild(canvas);
    }

    protected abstract void handleAttr(TypedArray typedArray);

    //visualization 音频回调
    @Override
    public void onFftDataCapture(float[] parseData) {

    }

    //audio准备完毕
    @Override
    public void onPrepare() {

    }

    public void release() {
        if (mVisualizerHelper != null) {
            mVisualizerHelper.release();
        }
        if (mMediaManager != null) {
            mMediaManager.release();
        }
    }

    protected abstract void drawChild(Canvas canvas);
}
