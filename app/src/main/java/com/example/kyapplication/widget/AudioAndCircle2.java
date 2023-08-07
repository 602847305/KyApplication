package com.example.kyapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.example.kyapplication.utils.F;
import com.example.kyapplication.utils.SavitzkyGolayFilter;

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
                float endX = startX + 1.5f*getWaveData()[i] * (float) Math.cos(angle);
                float endY = startY + 1.5f*getWaveData()[i] * (float) Math.sin(angle);
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
                float endX = startX + 1.5f*waveData * (float) Math.cos(angle);
                float endY = startY + 1.5f*waveData * (float) Math.sin(angle);
                canvas.drawLine(startX, startY, endX, endY, linePaint);
            }
        }


    }

    protected float[] getWaveData()
    {

//        int windowSize = 5;
//        int polynomialOrder = 2;
//
//        double[] output = savitzkyGolay(input, windowSize, polynomialOrder);
        return changeWaveData(mWaveData);
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
        float[] newWaveData = smooth(waveData,5);
//        float[] newWaveData = SavitzkyGolayFilter.savitzkyGolay(waveData,5, 4);

        return newWaveData;
    }

    /**
     * 数据平滑
     * @param data float[] 频谱
     * @param windowSize 窗口大小
     * @return 处理结果
     */
    public static float[] smooth(float[] data, int windowSize) {
            if (windowSize <= 0) {
                throw new IllegalArgumentException("Window size must be greater than zero.");
            }
            float[] smoothedData = new float[data.length];

            for (int i = 0; i < data.length; i++) {
                int sum = 0;
                int count = 0;

                for (int j = Math.max(0, i - windowSize + 1); j <= Math.min(i + windowSize - 1, data.length - 1); j++) {
                    sum += data[j];
                    count++;
                }
                smoothedData[i] = (float) (sum / count);
            }


            float[] smoothedData2 = new float[data.length];
            int index_length = (int)(smoothedData2.length/4);

            int ind = 0;
            for (int i=0;i<smoothedData2.length;i++)
            {
                if(i<data.length-index_length) {
                    smoothedData2[i] = smoothedData[index_length + i];

                }else {
                    smoothedData2[i] = smoothedData[ind];
                    ind++;
                }

            }


            return smoothedData;
    }




//        public static void main(String[] args) {
            float[] data = {1.2f, 2.6f, 3.8f, 4.1f, 5.9f, 6.3f, 7.5f, 8.0f, 9.4f};



//
//            float[] smoothedData = smooth(data, 3);
//
//        }

        public static float[] smoothStartAndEnd(float[] data, int windowSize) {
            int dataLength = data.length;
            float[] smoothedData = new float[dataLength];

            // Smooth data for middle part
            for (int i = 0; i < dataLength; i++) {
                int start = Math.max(0, i - windowSize);
                int end = Math.min(dataLength - 1, i + windowSize);
                int count = end - start + 1;

                float sum = 0;
                for (int j = start; j <= end; j++) {
                    sum += data[j];
                }

                smoothedData[i] = sum / count;
            }

            // Smooth data for start part
            for (int i = 0; i < windowSize; i++) {
                int end = i + windowSize;
                int count = end + 1;

                float sum = 0;
                for (int j = 0; j <= end; j++) {
                    sum += data[j];
                }

                smoothedData[i] = sum / count;
            }

            // Smooth data for end part
            for (int i = dataLength - windowSize; i < dataLength; i++) {
                int start = i - windowSize;
                int count = dataLength - start;

                float sum = 0;
                for (int j = start; j < dataLength; j++) {
                    sum += data[j];
                }

                smoothedData[i] = sum / count;
            }

            return smoothedData;
        }





        public static void sd(String[] args) {
            int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

            // 移动前五位的值到末尾
            int[] newArr = new int[arr.length];
            for (int i = 0; i < arr.length - 5; i++) {
                newArr[i] = arr[i + 5];
            }
            for (int i = 0; i < 5; i++) {
                newArr[arr.length - 5 + i] = arr[i];
            }

            // 打印移动后的数组
            for (int num : newArr) {
                System.out.print(num + " ");
            }
        }


}