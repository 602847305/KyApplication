package com.example.kyapplication.widget;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.kyapplication.R;

import java.io.IOException;

public class MusicalWave2 extends View {
    private Visualizer visualizer;
    private Context context;
    private String music = "music1.mp3";
    private int mCount = 20;
    private  Paint innerPaint, outPaint;
    private int roundCount = 100;//每一圈射线的个数
    private int bgRayWidth,dynamicRayWidth;//背景射线的宽度,动态射线的宽度
    private int bgRayHeight = 20;//射线长度

    public void init(Context context,String music,int roundCount)
    {
        this.context = context;
        this.music = music;
        MediaPlayer mediaPlayer = new MediaPlayer();
        AssetManager assetManager = context.getAssets();
        this.roundCount = roundCount;
        try {
            AssetFileDescriptor descriptor = assetManager.openFd(this.music);
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {

            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
                float[] model = new float[fft.length / 2 + 1];
                model[0] = (float) Math.abs(fft[1]);
                int j = 1;

                for (int i = 2; i < mCount *2;) {

                    model[j] = (float) Math.hypot(fft[i], fft[i + 1]);
                    i += 2;
                    j++;
                    model[j] = Math.abs(model[j]);
                }
                invalidate();
            }
        },Visualizer.getMaxCaptureRate()/2,false,true);
        visualizer.setEnabled(true);
    }
    public MusicalWave2(Context context) {
        super(context);
    }

    public MusicalWave2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MusicalWave2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        outPaint = new Paint();
        outPaint.setColor(typedArray.getColor(R.styleable.CircleView_outColor, Color.GREEN));
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setStrokeWidth(bgRayHeight);
        outPaint.setAntiAlias(true);
//        outProgress = typedArray.getInteger(R.styleable.CircleView_outProgress, 90);

    }

    public void release()
    {
     if (visualizer!=null)
     {
         visualizer.release();
     }
    }
    private RectF rectF;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int viewHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int rectLength = (int) (((Math.min(viewHeight, viewWidth))));
        if(rectF ==null)
        rectF = new RectF(0, 0, viewWidth, viewHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rectF, -90f, -70, false, outPaint);
        canvas.drawArc(rectF, 180, 190, false, outPaint);
    }
}