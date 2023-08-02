package com.example.kyapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.example.kyapplication.ui.fragment.MusicFragment;
import com.example.kyapplication.utils.F;

public class MusicalWave2 extends View {
    private final static String  TAG = "MusicalWave";
    private Paint linePaint;
    private float centerX,centerY;
    protected float radius;
    private int numRays;
    private float rayLength;
    private float[] waveData;
    public MusicalWave2(Context context) {
        this(context, null);
    }
    public MusicalWave2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MusicalWave2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        F.d("````````init`````");
        @ColorInt int LINE = 0xFFd5e8e8;
        linePaint = new Paint();
        linePaint.setColor(LINE);
        linePaint.setStrokeWidth(10);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        numRays = 100;
        rayLength = 88;
    }
    public void setWaveData(float[] waveData)
    {
//        this.waveData = waveData;
        invalidate();

    }
    public void init(Context context)
    {
//        F.d("````````init`````");
//        @ColorInt int LINE = 0xFFd5e8e8;
//        linePaint = new Paint();
//        linePaint.setColor(LINE);
//        linePaint.setStrokeWidth(10);
//        linePaint.setStyle(Paint.Style.STROKE);
//        linePaint.setStrokeCap(Paint.Cap.ROUND);
//        numRays = 100;
//        rayLength = 88;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2f;
        centerY = h/2f;
        radius = Math.min(w,h) * 0.3f;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < numRays; i++) {
            if(MusicFragment.mWaveData ==null) {
                rayLength=1;
            }else {
                rayLength = MusicFragment.mWaveData[i+1];
            }
            float angle = (2 * (float) Math.PI * i) / numRays;
            float startX = centerX + radius * (float) Math.cos(angle);
            float startY = centerY + radius * (float) Math.sin(angle);
            float endX = startX + rayLength * (float) Math.cos(angle);
            float endY = startY + rayLength * (float) Math.sin(angle);
            canvas.drawLine(startX, startY, endX, endY, linePaint);
        }
    }
}