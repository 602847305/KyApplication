package com.example.kyapplication.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.kyapplication.R;
import com.example.kyapplication.utils.F;

import java.io.ByteArrayOutputStream;

/**
 * 为音乐展示而开发的自定义控件
 */
public class MusicWave extends View {

    private Bitmap mImageBitmap;
    private Paint mPaint = new Paint();
    private Path mPath = new Path();

    int quality = 100; // 压缩质量，范围为0-100，100表示最高质量

    private int viewHeight;
    private int viewWidth;

    public MusicWave(Context context) {
        super(context);

    }

    public MusicWave(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MusicWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    float oldBitmapWidth;
    float oldBitmapHeight;

    private void init() {
        mPaint.setAntiAlias(true);//抗锯齿
        // 加载图片
        mImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.music_gloria2);
        oldBitmapWidth =  mImageBitmap.getWidth();
        oldBitmapHeight =  mImageBitmap.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        F.d("```widthMeasureSpec="+widthMeasureSpec
                +"\n  ```heightMeasureSpec="+heightMeasureSpec
        );
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }


    Bitmap bitmap;
    float bitmapScaleX;
    float bitmapScaleY;

    Bitmap compressedBitmap;//压缩后的bitmap

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mImageBitmap!=null)
        {
            // 计算图片缩放比例
            bitmapScaleX =  getWidth();
            bitmapScaleY  =  getHeight();
            float scale = Math.min(bitmapScaleX, bitmapScaleY);
            //对bitmap进行缩放
            bitmap = Bitmap.createScaledBitmap(mImageBitmap,(int)scale,(int)scale,true);
            quality = 100 * (getHeight()/mImageBitmap.getHeight());

            if (quality > 100)
            {
                quality = 100;

            } else if (quality<20) {
                quality =100;
            }
            F.d("````quality```````"+quality);
            if(mImageBitmap!=null)//释放资源
            {
                mImageBitmap.recycle();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            byte[] compressedByteArray = baos.toByteArray();
            compressedBitmap = BitmapFactory.decodeByteArray(compressedByteArray, 0, compressedByteArray.length);
            if(bitmap!=null)//释放资源
            {
                bitmap.recycle();
            }
        }
        // 以画布中心点为坐标原点，平移画布
        canvas.translate(getWidth() / 2f, getHeight() / 2f);
//        // 缩放画布
//        canvas.scale(scale, scale);

        // 裁剪为圆形路径
        mPath.reset();
        RectF rectF = new RectF(-compressedBitmap.getWidth() / 2f, -compressedBitmap.getHeight() / 2f,
                compressedBitmap.getWidth() / 2f, compressedBitmap.getHeight() / 2f);
        mPath.addOval(rectF, Path.Direction.CW);
        canvas.clipPath(mPath);

        // 绘制图片
        canvas.drawBitmap(compressedBitmap, -compressedBitmap.getWidth() / 2f, -compressedBitmap.getHeight() / 2f, mPaint);
    }
}
