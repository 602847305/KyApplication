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
        F.d("````````1``````");
    }

    public AudioAndCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        F.d("``````2````````");

        @ColorInt int LINE = 0xFFd5e8e8;
        linePaint = new Paint();
        linePaint.setColor(LINE);
        linePaint.setStrokeWidth(10);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public AudioAndCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        F.d("```````3```````");


    }

    @Override
    protected void handleAttr(TypedArray typedArray) {

    }

    @Override
    protected void drawChild(Canvas canvas) {
        int rayLength = 1;
        for (int i = 0; i < numRays; i++) {
            float angle = (2 * (float) Math.PI * i) / numRays;
            float startX = centerX + radius * (float) Math.cos(angle);
            float startY = centerY + radius * (float) Math.sin(angle);
            float endX = startX + rayLength * (float) Math.cos(angle);
            float endY = startY + rayLength * (float) Math.sin(angle);
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
    }
    private int radius = 100;

    /**
     * 圆形到射线起始位置的长度，即内圆的半径
     * @param radius 半径
     */
    public void setRadius(int radius)
    {
        this.radius = radius;
    }


}
