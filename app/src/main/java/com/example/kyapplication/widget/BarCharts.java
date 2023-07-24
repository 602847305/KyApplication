package com.example.kyapplication.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.kyapplication.bean.BarChartsData;
import com.example.kyapplication.utils.F;

import java.util.ArrayList;

public class BarCharts extends View {

    private Paint mCirclePaint;
    private TextPaint mTextPaint;
    private Paint mLinePaint;
    private Paint pillarPaint;

    private Rect textBounds;



    private void init(){
        mCirclePaint = new Paint();
        mCirclePaint.setStyle(Paint.Style.FILL);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(30f);
        mTextPaint.setAntiAlias(true);

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(3);

        pillarPaint = new Paint();
        pillarPaint.setColor(Color.BLUE);
        pillarPaint.setStyle(Paint.Style.STROKE);




        path= new Path();

        textBounds = new Rect();

    }

    public BarCharts(Context context) {
        this(context,null);
    }

    public BarCharts(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BarCharts(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private Path path;
    private int viewWidth,viewHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewWidth = getMeasuredWidth() -getPaddingLeft() - getPaddingRight();
        viewHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

//        drawBarChart(50f);
        F.d("onMeasure end");
    }

    /**
     * 左边刻度底部 Y 轴
     */
    float yScale;

    /**
     * 左边刻度间隔
     */
    float xScale ;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i=1;i<=dataSize;i++)
        {
            canvas.drawText(String.valueOf(maxScaleLabel/scaleSize*i),getPaddingLeft(),yScale-xScale*i,mTextPaint);
        }
        canvas.drawPath(path,mLinePaint);

        if( pillPath==null)
            return;

        canvas.drawPath(pillPath,pillarPaint);
    }

    /**
     * 数据信息
     */
    private ArrayList<BarChartsData> arrayList;

    /**
     * 最大的数据
     */
    private int maxNumberData;

    /**
     * 标尺最大刻度
     */
    private int maxScaleLabel;

    /**
     * 标尺刻度数量,不包括"0"
     */
    private int scaleSize;

    private int dataSize;

    public void setData(ArrayList<BarChartsData> arrayList,int maxScaleLabel,int scaleSize)
    {
        this.arrayList = arrayList;
        for(BarChartsData data:arrayList)
        {
            if(data.getNumber()>maxNumberData)
                maxNumberData = data.getNumber();
        }
        this.scaleSize = scaleSize;
        this.maxScaleLabel = Math.max(maxNumberData,maxScaleLabel);
        this.dataSize = arrayList.size();
        F.d("setData end");
        drawBarChart(50f);
    }

    private float xLeftBottom,yLeftBottom;

    private float yLength;//Y轴最大标尺0-max的距离

    public void drawBarChart(float pillarWidth)
    {
        //测量出数字的长度
        mTextPaint.getTextBounds(String.valueOf(maxScaleLabel),0,String.valueOf(maxScaleLabel).length(),textBounds);
        //左下角X,Y坐标
        xLeftBottom = getPaddingLeft()+textBounds.width()*1.2f;
        yLeftBottom = getPaddingTop()+viewHeight-textBounds.height()*1.2f;

        //将path路径移动到左上角起点,x必须加上数字测量后的距离
        path.moveTo(xLeftBottom,getPaddingTop());
        path.lineTo(xLeftBottom,yLeftBottom);
        path.lineTo(xLeftBottom+viewWidth,yLeftBottom);


        yLength=(viewHeight-textBounds.height()*1.2f)*0.9f;

        //"Y"轴刻度间距
        xScale = yLength/scaleSize;

        yScale = yLeftBottom;
        //绘制刻度
        for (int i=1;i<=scaleSize;i++)
        {
            path.moveTo(xLeftBottom,yScale-xScale*i);
            path.lineTo(xLeftBottom+30,yScale-xScale*i);
        }

        //"X"轴长度
        float xScaleLength = viewWidth-xLeftBottom;
        //"X"轴间隔
        float xScaleXInterval = xScaleLength/(dataSize+1);

        pillarPaint.setStrokeWidth(pillarWidth);
        pillPath = new Path();

//        for(int i=1;i<=dataSize;i++)
//        {
//            pillPath.moveTo(xLeftBottom+pillarWidth/2+xScaleXInterval*i,yLeftBottom);
//            float Y = yLeftBottom - (arrayList.get(i-1).getNumber()*1f/maxScaleLabel)*yLength;
//            pillPath.lineTo(xLeftBottom+pillarWidth/2+xScaleXInterval*i,Y);
//        }
//        invalidate();

        pillTime(2000,0,1,pillarWidth,xScaleXInterval);

    }
    private Path pillPath;

    public void pillTime(int allTime, float start, float end,float pillarWidth,float xScaleXInterval) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float length = (float) animation.getAnimatedValue();

        for(int i=1;i<=dataSize;i++)
        {
            pillPath.moveTo(xLeftBottom+pillarWidth/2+xScaleXInterval*i,yLeftBottom);
            float Y = yLeftBottom - (arrayList.get(i-1).getNumber()*1f/maxScaleLabel)*yLength*length;
            pillPath.lineTo(xLeftBottom+pillarWidth/2+xScaleXInterval*i,Y);
        }
                invalidate();
            }
        });
//        animator.setInterpolator(new OvershootInterpolator());
        animator.setDuration(allTime);
        animator.start();
    }





    public void drawLine()
    {

    }

    private void setXY()
    {

    }

}
