package com.example.kyapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.kyapplication.utils.F;


class BitmapView extends View {
    Drawable drawable;

    int[] colors = new int[]{Color.BLUE, Color.RED, Color.GREEN};
    float[] points = new float[]{0f, 0.5f, 1f};

    public BitmapView(Context context) {
        this(context,null);
    }

    public BitmapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    Bitmap bitmap;

    Paint paint;
    private Bitmap bitmapForColor;
    public BitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        LinearGradient mBarShader;
        mBarShader = new LinearGradient(0, 0, getMeasuredWidth(), 0, colors, points, Shader.TileMode.CLAMP);
        paint.setShader(mBarShader);
        bitmap = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);

//        paint.setColor(Color.RED);

        bitmapForColor = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);

        //Android4.0（API14）之后硬件加速功能就被默认开启了,setShadowLayer 在开启硬件加速的情况下无效，需要关闭硬件加速
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        GradientDrawable dynamicDrawable = new GradientDrawable();
        dynamicDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        dynamicDrawable.setUseLevel(false);

        int colors[] = new int[3];
        colors[0] = Color.parseColor("#711234");
        colors[1] = Color.parseColor("#269869");
        colors[2] = Color.parseColor("#269869");
        dynamicDrawable.setColors(colors);

        Canvas canvas = new Canvas(bitmap);
        dynamicDrawable.draw(canvas);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        bitmapForColor = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        rect.set(0,0,50,50);

    }

    Rect rect = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmapForColor, null, rect, null);

        int i =bitmap.getPixel(bitmap.getWidth()/2,bitmap.getHeight()/2);
        F.d(i);

    }

}
