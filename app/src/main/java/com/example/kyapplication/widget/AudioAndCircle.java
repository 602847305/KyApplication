package com.example.kyapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.example.kyapplication.utils.F;

public class AudioAndCircle extends BaseAudioVisualizeView{
    private Paint linePaint;
    private int numRays = 60;  //
    public AudioAndCircle(Context context) {
        super(context);
    }

    public AudioAndCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        @ColorInt int LINE = 0xFFd5e8e8;
        linePaint = new Paint();
        linePaint.setColor(LINE);
        linePaint.setStrokeWidth(6);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public AudioAndCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void handleAttr(TypedArray typedArray) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2f;
        centerY = h/2f;
        radius = Math.min(w,h) * 0.26f;
    }

    private float waveDataIndex = 0f;
    private float[] waveDataOld;
    @Override
    protected void drawChild(Canvas canvas) {
        if (waveDataOld ==null || waveDataIndex!=getWaveData()[20])
        {

            waveDataOld = getWaveData();
            waveDataIndex = waveDataOld[20];
            for (int i = 0; i < numRays; i++) {
                float angle = (2 * (float) Math.PI * i) / numRays;
                float startX = centerX + radius * (float) Math.cos(angle);
                float startY = centerY + radius * (float) Math.sin(angle);
                float endX = startX + getWaveData()[i] * (float) Math.cos(angle);
                float endY = startY + getWaveData()[i] * (float) Math.sin(angle);
                canvas.drawLine(startX, startY, endX, endY, linePaint);
            }
        }else
//            if(waveDataOld[30] ==getWaveData()[30])
        {
            for (int i = 0; i < numRays; i++) {
                float waveData = waveDataOld[i]-0.3f;
                if (waveData <0)
                {
                    waveData = 1f;
                }
                waveDataOld[i] = waveData;
                float angle = (2 * (float) Math.PI * i) / numRays;
                float startX = centerX + radius * (float) Math.cos(angle);
                float startY = centerY + radius * (float) Math.sin(angle);
                float endX = startX + waveData * (float) Math.cos(angle);
                float endY = startY + waveData * (float) Math.sin(angle);
                canvas.drawLine(startX, startY, endX, endY, linePaint);
            }
        }


    }


    /**
     * 射线数量
     * @param num 数量
     */
    public void setNumRays(int num)
    {
        this.numRays = num;
        setSpectrumCount(num);
    }
    private float radius = 100;

    /**
     * 圆形到射线起始位置的长度，即内圆的半径
     * @param radius 半径
     */
    public void setRadius(int radius)
    {
        this.radius = radius;
    }


}
