package com.example.kyapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.kyapplication.utils.F;

public class Triangles  extends View {
    private Paint mPaint ;
    private Path mPath;

    public Triangles(Context context) {
        this(context,null);
    }

    public Triangles(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public Triangles(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        F.d("Triangles()");
        mPaint= new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        F.d("onDraw()");
        int width = getWidth();
        int height = getHeight();
        mPath.reset();
        mPath.moveTo(41,0);
        mPath.moveTo(66,77);
        mPath.lineTo(0,77);
        mPath.close();
//        canvas.drawPath(mPath,mPaint);
        canvas.drawCircle(300, 300, 200, mPaint);
    }
}
