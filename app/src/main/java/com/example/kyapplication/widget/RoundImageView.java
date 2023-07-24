package com.example.kyapplication.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.kyapplication.R;
import com.example.kyapplication.utils.F;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RoundImageView extends View {
    private Context context;
    private Bitmap mImageBitmap;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mPath = new Path();
    private ImageView mImageView;
    private int angel;
    int quality = 100; // 压缩质量，范围为0-100，100表示最高质量

    private String music = "music1.mp3";

    private int viewHeight;
    private int viewWidth;

    public RoundImageView(Context context) {
        super(context);
        init(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    float oldBitmapWidth;
    float oldBitmapHeight;

    private void init(Context context) {
        this.context = context;
        angel = 0;
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
        F.d("``width```"+width);
        F.d("``height```"+height);
        setMeasuredDimension(width, height);
    }


    Bitmap bitmap;
    float bitmapScaleX;
    float bitmapScaleY;

    //    Bitmap compressedBitmap;//压缩后的bitmap
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        ImageView imageView = findViewById(R.id.imageView);
//        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
//        imageView.startAnimation(rotateAnimation);

        if(mImageBitmap!=null)
        {
            // 计算图片缩放比例
            bitmapScaleX =  getWidth()*1.0f;
            bitmapScaleY  =  getHeight()*1.0f;
            float scale = Math.min(bitmapScaleX, bitmapScaleY);
            //对bitmap进行缩放
            bitmap = Bitmap.createScaledBitmap(mImageBitmap,(int)scale,(int)scale,true);
            F.d("````getHeight()="+getHeight());
            F.d("````mImageBitmap.getHeight()="+mImageBitmap.getHeight());
            quality = 100 * (getHeight()/mImageBitmap.getHeight());


            F.d("````quality="+quality);
            if (quality > 100)
            {
                quality = 100;

            } else if (quality<20) {
                quality =100;
            }

            if(mImageBitmap!=null)//释放资源
            {
                mImageBitmap.recycle();
                mImageBitmap=null;
            }
        }
        // 以画布中心点为坐标原点，平移画布
        canvas.translate(getWidth() / 2f, getHeight() / 2f);
//        // 缩放画布
//        canvas.scale(scale, scale);

        // 裁剪为圆形路径
        mPath.reset();
        RectF rectF = new RectF(-bitmap.getWidth() / 2f, -bitmap.getHeight() / 2f,
                bitmap.getWidth() / 2f, bitmap.getHeight() / 2f);
        mPath.addOval(rectF, Path.Direction.CW);
        canvas.clipPath(mPath);

        // 绘制图片
//        canvas.rotate(angel,getMeasuredWidth()/2f,getMeasuredHeight()/2f);
        canvas.rotate(angel);
        canvas.drawBitmap(bitmap, -bitmap.getWidth() / 2f, -bitmap.getHeight() / 2f, mPaint);
//        invalidate();
//        angel++;
//        if (angel > 360)
//        {
//            angel = 1;
//        }
    }

//    /**
//     * 旋转图片
//     */
//    public void runRotate()
//    {
//        invalidate();
//    }
}
