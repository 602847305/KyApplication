package com.example.kyapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.LogRecord;

/**
 * 生成移动的三角形饰品
 */
public class AudioAndCircle3 extends BaseAudioVisualizeView {
    private static final int MAX_TRIANGLES = 50;
    private static final int MIN_SIDE_LENGTH = 5;
    private static final int MAX_SIDE_LENGTH = 15;

    private List<Triangle> mTriangleList;
    private Paint mPaint;
    private Handler mHandler;
    private int alpha;

    public AudioAndCircle3(Context context) {
        super(context);
    }

    public AudioAndCircle3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTriangleList = new ArrayList<>();
        mPaint=new Paint();
        mHandler = new Handler();
        alpha = 255;
        // 开始生成三角形
        generateTriangle();
    }

    public AudioAndCircle3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void generateTriangle() {
        if (mTriangleList.size() < MAX_TRIANGLES) {
            Random random = new Random();
            int sideLength = random.nextInt(MAX_SIDE_LENGTH - MIN_SIDE_LENGTH) + MIN_SIDE_LENGTH;

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            double angle = Math.toRadians(random.nextInt(360));
            int x = (int) (centerX + Math.sin(angle) * (getWidth() / 2 - sideLength));
            int y = (int) (centerY + Math.cos(angle) * (getHeight() / 2 - sideLength));

            Triangle triangle = new Triangle(x, y, sideLength);
            mTriangleList.add(triangle);

            if (alpha > 0) {
                alpha -= 5;
            }

            mPaint.setColor(Color.argb(alpha, 0, 0, 0));

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    generateTriangle();
                    invalidate();
                }
            }, 500);
        }
    }

    @Override
    protected void handleAttr(TypedArray typedArray) {

    }

    @Override
    protected void drawChild(Canvas canvas) {


        for (Triangle triangle : mTriangleList) {
            Path path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.moveTo(triangle.getX(), triangle.getY());
            path.lineTo(triangle.getX() + triangle.getSideLength(), triangle.getY());
            path.lineTo(triangle.getX() + (triangle.getSideLength() / 2), triangle.getY() - triangle.getSideLength());
            path.close();
            canvas.drawPath(path, mPaint);
        }

    }

    private class Triangle{
        private final int x;
        private final int y;
        private final int sideLength;
        public Triangle(int x,int y,int sideLength)
        {
            this.x = x;
            this.y = y;
            this.sideLength = sideLength;
        }

        public int getX(){return  x;}
        public int getY(){return  y;}
        public int getSideLength(){return sideLength;}

    }

}
