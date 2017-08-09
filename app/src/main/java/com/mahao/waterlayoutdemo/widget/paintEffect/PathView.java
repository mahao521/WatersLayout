package com.mahao.waterlayoutdemo.widget.paintEffect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.SumPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mahao on 17/8/1.
 */

public class PathView extends View {


    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //线条的末端--方形和圆形
        //drawStrokeCap(canvas);


        //线条链接拐弯处---尖角和圆角
        //drawStrokeJoin(canvas);


        //线条链接处---设置角度
        //drawCornerPathEffect(canvas);

        //drawCornerPathEffectDemo(canvas);


       // drawDashPathEffectDemo(canvas);


        //drawDiscretePathEffectDemo(canvas);


        //drawPathDashPathEffect(canvas);


       // drawPathDashPathEffectDemo(canvas);

       // drawComposePathEffect(canvas);

        drawSubPixText(canvas);

    }

    private void drawSubPixText(Canvas canvas) {



          Paint paint = new Paint();
          paint.setColor(Color.GREEN);
          String text = "我要超越自己";
          paint.setTextSize(100);
          paint.setSubpixelText(false);

          canvas.drawText(text,0,200,paint);


          //设置亚像素---可以使文字更清晰。
          canvas.translate(0,300);
          paint.setSubpixelText(true);
          paint.setUnderlineText(true);
          canvas.drawText(text,0,200,paint);
    }

    private void drawComposePathEffect(Canvas canvas) {


        Paint paint = getPaint();
        Path path = getPath();
        canvas.drawPath(path,paint);


        //圆角路径
        canvas.translate(0,300);
        CornerPathEffect cornerPathEffect = new CornerPathEffect(100);
        paint.setPathEffect(cornerPathEffect);
        canvas.drawPath(path,paint);

        //虚线
        canvas.translate(0,300);
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{2,5,10,10},0);
        paint.setPathEffect(dashPathEffect);
        canvas.drawPath(path,paint);

        canvas.translate(0,300);

        //先应用圆角特效，再应用虚线特效
        ComposePathEffect composePathEffect = new ComposePathEffect(dashPathEffect,cornerPathEffect);
        paint.setPathEffect(composePathEffect);
        canvas.drawPath(path,paint);


        canvas.translate(0,300);
        //叠加2条特效；没有先后关系
        paint.setStyle(Paint.Style.STROKE);
        SumPathEffect sumPathEffect = new SumPathEffect(dashPathEffect,cornerPathEffect);
        paint.setPathEffect(sumPathEffect);
        canvas.drawPath(path,paint);



    }

    private void drawPathDashPathEffectDemo(Canvas canvas) {

        Paint paint = getPaint();
        Path path = getPath();
        canvas.drawPath(path,paint);
        canvas.translate(0,200);

        paint.setPathEffect(new PathDashPathEffect(getstampPath(),35,0,PathDashPathEffect.Style.MORPH));
        canvas.drawPath(path,paint);


        canvas.translate(0,200);
        paint.setPathEffect(new PathDashPathEffect(getstampPath(),35,0,PathDashPathEffect.Style.ROTATE));
        canvas.drawPath(path,paint);

        canvas.translate(0,200);
        paint.setPathEffect(new PathDashPathEffect(getstampPath(),35,0,PathDashPathEffect.Style.TRANSLATE));
        canvas.drawPath(path,paint);


    }

    private void drawPathDashPathEffect(Canvas canvas) {

        Paint paint = getPaint();
        Path path = new Path();
        path.moveTo(100,600);
        path.lineTo(400,150);
        path.lineTo(700,900);
        canvas.drawPath(path,paint);

        canvas.translate(0,200);

        /**
         *   第一个参数 ： 形状
         *   第二个参数 ： 路径上每个形状的距离
         *   第三个参数 ： 每个形状的偏移量
         *   第四个参数 ： 每个图形的运动方式，平移，翻转。
         */
        paint.setPathEffect(new PathDashPathEffect(getstampPath(),35,0,PathDashPathEffect.Style.MORPH));
        canvas.drawPath(path,paint);

    }


    private Path getstampPath(){

        Path path = new Path();
        path.moveTo(0,20);
        path.lineTo(10,0);
        path.lineTo(20,20);
        path.close();

        path.addCircle(0,0,3,Path.Direction.CCW);

        return path;

    }




    private void drawDiscretePathEffectDemo(Canvas canvas) {


        Paint paint = getPaint();
        Path path = getPath();
        canvas.drawPath(path,paint);

        /**
         *
         *  把原有的路线，在制定的距离处插入一个突刺
         *
         *  第一个参数这些突出的"杂点"的距离，值越小，距离越短，越密集。
         *
         *  第二个参数是突出的距离
         */

        canvas.translate(0,200);
        paint.setPathEffect(new DiscretePathEffect(20,5));
        canvas.drawPath(path,paint);


        canvas.translate(0,200);
        paint.setPathEffect(new DiscretePathEffect(36,5));
        canvas.drawPath(path,paint);

        canvas.translate(0,200);
        paint.setPathEffect(new DiscretePathEffect(22,15));
        canvas.drawPath(path,paint);

    }


    private void drawDashPathEffectDemo(Canvas canvas) {

        Paint paint = getPaint();
        Path path = getPath();
        canvas.translate(0,100);

        //画15dp的线段间隙20dp再画15dp再间隔15。重复这个方法。 phase偏移量。
        paint.setPathEffect(new DashPathEffect(new float[]{15,20,15,15},0));
        canvas.drawPath(path,paint);

    }


    private void drawCornerPathEffectDemo(Canvas canvas) {


        Paint paint = getPaint();
        Path path = getPath();
        canvas.drawPath(path,paint);

        paint.setPathEffect(new CornerPathEffect(200));
        canvas.save();
        canvas.translate(0,150);
        canvas.drawPath(path,paint);
    }


    private void drawCornerPathEffect(Canvas canvas) {

        Paint paint = getPaint();
        Path path = new Path();
        path.moveTo(100,600);
        path.lineTo(400,100);
        path.lineTo(800,300);

        canvas.drawPath(path,paint);
        paint.setColor(Color.RED);
        paint.setPathEffect(new CornerPathEffect(100));
        canvas.drawPath(path,paint);

        paint.setPathEffect(new CornerPathEffect(200));
        paint.setColor(Color.YELLOW);
        canvas.drawPath(path,paint);

    }


    private void drawStrokeJoin(Canvas canvas) {


        Paint paint = new Paint();
        paint.setStrokeWidth(40);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);


        Path path = new Path();
        path.moveTo(100,100);
        path.lineTo(450,100);
        path.lineTo(100,300);
        paint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawPath(path,paint);


        path.moveTo(100,400);
        path.lineTo(450,400);
        path.lineTo(100,600);
        paint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawPath(path,paint);


        path.moveTo(100,700);
        path.lineTo(450,700);
        path.lineTo(100,900);
        paint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(path,paint);

    }


    /**
     *    绘制线条的结尾
     * @param canvas
     */
    private void drawStrokeCap(Canvas canvas) {


        Paint paint = new Paint();
        paint.setStrokeWidth(80);
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setDither(true);//是否设置都动处理，图片更更新和更饱满


        paint.setStrokeCap(Paint.Cap.BUTT);  //无线帽
        canvas.drawLine(100,200,400,200,paint);


        paint.setStrokeCap(Paint.Cap.SQUARE);//方形线帽
        canvas.drawLine(100,400,400,400,paint);


        paint.setStrokeCap(Paint.Cap.ROUND); //圆形线帽
        canvas.drawLine(100,600,400,600,paint);


    }

    private Paint getPaint(){

        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        return paint;

    }


    private Path getPath(){

        Path path = new Path();

        //定义路径的起点
        path.moveTo(0,0);

        //定义路径的各个点
        for(int i = 0; i <= 40; i++){

            path.lineTo(i*35,(int)(Math.random()*135));
        }
        return path;
    }


}
