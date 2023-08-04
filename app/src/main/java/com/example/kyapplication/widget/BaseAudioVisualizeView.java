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
import com.example.kyapplication.utils.F;
import com.example.kyapplication.visualize.VisualizeCallback;
import com.example.kyapplication.visualize.VisualizerHelper;

public abstract class  BaseAudioVisualizeView extends View implements VisualizeCallback, MediaManagerListener {
    private final  String  TAG = getClass().getName();
    private RectF mRect;
    private Paint mPaint;
    private Path mPath;
    //画笔颜色
    private int mColor;
    protected int mSpectrumCount;
    private float mSpectrumRatio;
    private float mItemMargin;
    protected float centerX, centerY;
    private MediaManager mediaManager;
    private VisualizerHelper mVisualizerHelper;
    //宽度
    private int mStrokeWidth;

    //音频波纹数据
    protected float[] mWaveData;

    public BaseAudioVisualizeView(Context context) {
        this(context,null);
    }

    public BaseAudioVisualizeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
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

        mediaManager = new MediaManager(getContext());
        mediaManager.setMediaManagerListener(this);

        mVisualizerHelper = new VisualizerHelper();
        mVisualizerHelper.setVisualizeCallback(this);
        mVisualizerHelper.setVisualCount(mSpectrumCount);
    }

    protected void setSpectrumCount(int count)
    {
        if(mVisualizerHelper!=null)
            mVisualizerHelper.setVisualCount(count);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mWaveData==null)
        {
            return;
        }
        drawChild(canvas);
    }

    public void play(String fileName)
    {
        if (mediaManager != null) {
            mediaManager.playByAssetsFile(getContext(),fileName);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    protected abstract void handleAttr(TypedArray typedArray);

    protected boolean isVisualizationEnabled = true;
    //visualization 音频回调
    @Override
    public void onFftDataCapture(float[] parseData) {

        if (!isVisualizationEnabled) {
            return;
        }
        mWaveData = parseData;
        invalidate();

    }

    //audio准备完毕
    @Override
    public void onPrepare() {
        try {
            int mediaPlayerId = mediaManager.getMediaPlayerId();
            mVisualizerHelper.setAudioSessionId(mediaPlayerId);
        } catch (Exception e) {
            F.d(e.getMessage());
        }
    }

    public void release() {
        if (mVisualizerHelper != null) {
            mVisualizerHelper.release();
        }
        if (mediaManager != null) {
            mediaManager.release();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    public void playWithSessionId(int audioSessionId) {
        try {
            mVisualizerHelper.setAudioSessionId(audioSessionId);
        } catch (Exception e) {
            F.d(e.getMessage());
        }
    }
    protected float[] getWaveData()
    {
        return mWaveData;
    }
    protected abstract void drawChild(Canvas canvas);
}
