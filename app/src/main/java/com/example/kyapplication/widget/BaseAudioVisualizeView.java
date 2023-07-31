package com.example.kyapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.kyapplication.R;
import com.example.kyapplication.media.MediaManager;
import com.example.kyapplication.media.MediaManagerListener;
import com.example.kyapplication.visualize.VisualizeCallback;
import com.example.kyapplication.visualize.VisualizerHelper;

public abstract class  BaseAudioVisualizeView extends View implements VisualizeCallback, MediaManagerListener {
    private final  String  TAG = getClass().getName();
    protected RectF mRect;
    protected Paint mPaint;
    protected Path mPath;
    protected float centerX, centerY;
    protected MediaManager mMediaManager;
    protected VisualizerHelper mVisualizerHelper;
    public BaseAudioVisualizeView(Context context) {
        this(context,null);
    }

    public BaseAudioVisualizeView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public BaseAudioVisualizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray typedArray = context.getTheme().obtainStyledAttributes(R.styleable.AudioVisualizeView,attrs,0);
    }

    //visualization 音频回调
    @Override
    public void onFftDataCapture(float[] parseData) {

    }

    //audio准备完毕
    @Override
    public void onPrepare() {

    }
}
