package com.example.kyapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class AudioAndCircle extends BaseAudioVisualizeView{
    public AudioAndCircle(Context context) {
        super(context);
    }

    public AudioAndCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AudioAndCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void handleAttr(TypedArray typedArray) {

    }

    @Override
    protected void drawChild(Canvas canvas) {
        for (int i = 0; i < count; i++) {
            double angel = ((360d / mSpectrumCount) * (i+1));
            double startX = centerX + (radius + mStrokeWidth/2) * Math.sin(Math.toRadians(angel));
            double startY = centerY + (radius + mStrokeWidth/2) * Math.cos(Math.toRadians(angel));
            double stopX = centerX + (radius + mStrokeWidth/2 + mSpectrumRatio * mRawAudioBytes[i]) * Math.sin(Math.toRadians(angel));
            double stopY = centerY + (radius + mStrokeWidth/2 + mSpectrumRatio * mRawAudioBytes[i]) * Math.cos(Math.toRadians(angel));
            canvas.drawLine((float) startX, (float) startY, (float) stopX, (float) stopY, mPaint);
        }
    }


}
