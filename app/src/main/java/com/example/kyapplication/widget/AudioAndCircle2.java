package com.example.kyapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.example.kyapplication.utils.F;

import java.util.Arrays;

public class AudioAndCircle2 extends BaseAudioVisualizeView{

    private Paint linePaint;
    private int numRays = 60;  //
    public AudioAndCircle2(Context context) {
        super(context);
    }

    public AudioAndCircle2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        @ColorInt int LINE = 0xFFd5e8e8;
        linePaint = new Paint();
        linePaint.setColor(LINE);
        linePaint.setStrokeWidth(6);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public AudioAndCircle2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    private float waveDataOld[] ;
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

    protected float[] getWaveData()
    {
//        changeWaveData(mWaveData);
        return mWaveData;
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

    private float bezierCure(int prev,int curr,int next)
    {
        int result = (prev+next)/2;
        return result;
    }

    private float[] changeWaveData(float[] waveData) {
        float[] newWaveData = new float[waveData.length];
        int[] index = findMaxFiveIndices(waveData,6);
        Arrays.sort(index);
        StringBuilder stringBuilder = new StringBuilder();
        for(int ind: index)
        {
            stringBuilder.append(ind).append(",");

        }
        F.d(stringBuilder);

        return newWaveData;
    }


        // 找出数组中最大的5个数的位置
        public static int[] findMaxFiveIndices(float[] numbers,int size) {
            int[] maxIndices = new int[size];

            for (int i = 0; i < size; i++) {
                int maxIndex = 0;
                for (int j = 1; j < numbers.length; j++) {
                    if (numbers[j] > numbers[maxIndex]) {
                        maxIndex = j;
                    }
                }
                maxIndices[i] = maxIndex;
                numbers[maxIndex] = Integer.MIN_VALUE;
            }

            return maxIndices;
        }

}