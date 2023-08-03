package com.example.kyapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.example.kyapplication.ui.fragment.MusicFragment;
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

    @Override
    protected void drawChild(Canvas canvas) {
        F.d("size:"+getWaveData().length);
        for (int i = 0; i < numRays; i++) {
            float angle = (2 * (float) Math.PI * i) / numRays;
            float startX = centerX + radius * (float) Math.cos(angle);
            float startY = centerY + radius * (float) Math.sin(angle);
            float endX = startX + 1.5f*getWaveData()[i] * (float) Math.cos(angle);
            float endY = startY + 1.5f*getWaveData()[i] * (float) Math.sin(angle);
            canvas.drawLine(startX, startY, endX, endY, linePaint);

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
