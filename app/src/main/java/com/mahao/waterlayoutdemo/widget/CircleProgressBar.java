package com.mahao.waterlayoutdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.mahao.waterlayoutdemo.R;

/**
 * Created by mahao on 17/7/25.
 */

public class CircleProgressBar extends View {


    private Paint paint;
    private int roundProgressColor;
    private int roundColor;
    private float textSize;
    private int textColor;
    private int max;
    private float roundWidth;
    private boolean isShow;

    private int progress;

    public CircleProgressBar(Context context) {
        this(context,null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initData(context,attrs);
    }

    private void initData(Context context,AttributeSet attributeSet) {


         paint = new Paint();

         TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleProgressBar);
         roundProgressColor = typedArray.getColor(R.styleable.CircleProgressBar_roundProgressColor, Color.BLUE);
         roundColor = typedArray.getColor(R.styleable.CircleProgressBar_roundColor, Color.RED);
         textSize = typedArray.getDimension(R.styleable.CircleProgressBar_textSize,76);
         textColor = typedArray.getColor(R.styleable.CircleProgressBar_textColor, Color.BLACK);
         max = typedArray.getInteger(R.styleable.CircleProgressBar_max,100);
         roundWidth = typedArray.getDimension(R.styleable.CircleProgressBar_roundWidth,55);
         isShow = typedArray.getBoolean(R.styleable.CircleProgressBar_textshow,true);

         typedArray.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //画背景圆环
        int center = getWidth()/2;
        float radius = center - roundWidth;
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置防抖动
        paint.setDither(true);
        canvas.drawCircle(center,center,radius,paint);


        //画圆环进度百分比
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);
        paint.setTypeface(Typeface.DEFAULT_BOLD);

        int percent = (int)(progress/(float)max * 100);
        String showText;
        if(percent >=1 && percent <= 100){

            showText = percent+"%";

        }else{

            showText = "点我开始绘制";
        }
        Paint.FontMetricsInt font = paint.getFontMetricsInt();
        //绘制text,纵坐标是y的baseLine开始绘制
        //baseY = center + (fm.bottom-fm.top)/2 - fm.bottom;
        // center view中心点   - fm.bottom/2 - fm.top/2
        //baseline为基准  top acssent 都为负值； bottom descent为正值

        canvas.drawText(showText,getWidth()/2-paint.measureText(showText)/2,getWidth()/2 - font.bottom/2 - font.top/2,paint);
        //画圆环填充百分比
        RectF rectF = new RectF(center-radius,center-radius,center+radius,center+radius);
        paint.setColor(roundProgressColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(roundWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);


        /**
         * @param useCenter If true, include the center of the oval in the arc, and
         *                  close it if it is being stroked. This will draw a wedge
         *
         *                 如果设置成true 形成闭合回路
         */
        canvas.drawArc(rectF,0,360*progress / max,false,paint);


    }


    /**
     *    通过线程不断改变绘制的百分比；
     * @param index
     */
    public void setProgress(int index){


        progress = index;

        postInvalidate();

    }



}
