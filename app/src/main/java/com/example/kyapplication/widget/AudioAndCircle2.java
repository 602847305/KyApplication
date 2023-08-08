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

    private float[] waveDataOld;
    @Override
    protected void drawChild(Canvas canvas) {
        if (isParseDataRefresh)
        {
            isParseDataRefresh = false;

            waveDataOld = getWaveData();
            for (int i = 0; i < numRays; i++) {
                float angle = (2 * (float) Math.PI * i) / numRays;
                float startX = centerX + radius * (float) Math.cos(angle);
                float startY = centerY + radius * (float) Math.sin(angle);
                float endX = startX + 2*getWaveData()[i] * (float) Math.cos(angle);
                float endY = startY + 2*getWaveData()[i] * (float) Math.sin(angle);
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
                float endX = startX + 2*waveData * (float) Math.cos(angle);
                float endY = startY + 2*waveData * (float) Math.sin(angle);
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
//        float[]data = removeMinValues(waveData,0.3);
        waveData[numRays-1] = waveData[1];
        waveData[numRays] = waveData[2];
        waveData[numRays+1] = waveData[3];
        waveData[numRays+2] = waveData[4];
        waveData[numRays+3] = waveData[5];
        waveData[numRays+4] = waveData[6];
        waveData[numRays+5] = waveData[7];
        waveData[numRays+6] = waveData[8];
        float[] newWaveData = smooth(waveData,4);
//        float[] newWaveData = SavitzkyGolayFilter.savitzkyGolay(waveData,5, 4);

        return newWaveData;
    }

    /**
     * 数据平滑
     * @param data float[] 频谱
     * @param windowSize 窗口大小
     * @return 处理结果
     */
    public float[] smooth(float[] data, int windowSize) {

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
//                sum+=data[i];
                smoothedData[i] = (float) (sum / count);
            }


//                int j = 0;
//                int zheng = (smoothedData.length/numRays);
//                float[] newData = new float[numRays];
//                for (int i = 0; i < numRays;) {
//                    float fen4 = 0f;
//                    for(int l=0;l<zheng;l++)
//                    {
//                        fen4+=smoothedData[i];
////                        j++;
//                        i++;
//                    }
//                    newData[j]=fen4/zheng;
//                    j++;
//
//                }


            return smoothedData;
    }




//        public static void main(String[] args) {




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

        private float[] remove30P(float[] data,int size)
        {
            Arrays.sort(data); // 对数组进行排序
            return Arrays.copyOfRange(data, size, data.length);
        }




        public float[] removeMinValues(float[] array, double percent) {
            if (percent <= 0 || percent >= 1) {
                throw new IllegalArgumentException("Invalid percentage value");
            }

            int numToRemove = (int) (array.length * percent);
            if (numToRemove == 0) {
                return array;
            }

            float[] sortedArray = array.clone();
            Arrays.sort(sortedArray);
            float[] result = Arrays.copyOfRange(sortedArray, numToRemove, array.length);

            return result;
        }


}