package com.mahao.waterlayoutdemo.widget.paintEffect;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.mahao.waterlayoutdemo.R;

/**
 * Created by mahao on 17/7/27.
 */

public class DashView extends View {


    private Paint paint;
    private Path path;
    private int phase = 100;

    private int dashIndex;

    public DashView(Context context) {
        this(context,null);
    }

    public DashView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initData();
    }

    private void initData() {

        paint = new Paint();
        //防抖动
        paint.setDither(true);
        //防锯齿
        paint.setAntiAlias(true);
        //设置画笔宽度
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.colorAccent));

        path = new Path();

        // 定义路径的各个点
        for (int i = 0; i <= 30; i++) {
            path.lineTo(i * 35, (float) (Math.random() * 100));
        }


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        canvas.drawPath(path,paint);
        paint.setColor(Color.GREEN);


        //连续画出虚线
        paint.setPathEffect(new DashPathEffect(new float[]{20,30},dashIndex));
        canvas.translate(0,100);
        canvas.drawPath(path,paint);


        Path pathDash = new Path();
        //ccw 逆时针
        pathDash.moveTo(90, 340);
        pathDash.lineTo(150, 340);
        pathDash.lineTo(120, 290);
        pathDash.close();
     //   pathDash.addArc(new RectF(1,3,6,11),0,360);
        /**
         * @param shape The path to stamp along
         * @param advance spacing between each stamp of shape
         * @param phase amount to offset before the first shape is stamped
         * @param style how to transform the shape at each position as it is stamped
         */
        //连续画出虚线，通过图形连续的画出
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new PathDashPathEffect(pathDash,300,
                dashIndex,PathDashPathEffect.Style.ROTATE));
        canvas.translate(0,300);
        canvas.drawPath(path,paint);

        if(phase > 0){
            phase--;
        }else{
            phase = 100;
        }
       // invalidate();
    }


    public void startAnim(){


        ValueAnimator animator = ValueAnimator.ofInt(0,230);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                dashIndex = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}













