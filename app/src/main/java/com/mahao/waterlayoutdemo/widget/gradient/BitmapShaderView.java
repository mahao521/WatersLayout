package com.mahao.waterlayoutdemo.widget.gradient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mahao.waterlayoutdemo.R;

/**
 * Created by mahao on 17/8/3.
 */

public class BitmapShaderView extends View {


    private Paint paint;
    private Bitmap bitmap;
    private int width;
    private int height;
    private int[] colors ={Color.RED,Color.GREEN,Color.BLUE};

    private int phase = 0;

    public BitmapShaderView(Context context) {
        this(context,null);
    }

    public BitmapShaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BitmapShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initData(context);
    }

    private void initData(Context context) {


        paint = new Paint();
        bitmap = ((BitmapDrawable)context.getResources().getDrawable(R.mipmap.rion)).getBitmap();

        width = bitmap.getWidth();
        height = bitmap.getHeight();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        /**
         *  shader的三种模式
         *       TileMode.ClAMP 拉伸最后一个像素铺满剩下的地方
         *       TileMode.MIRROR 通过镜像翻转铺满剩下的地方
         *       TileMode.REPEAT 重复图片铺满整个画面
         */

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        //获取最大的缩放比
        float scale = Math.max(width,height)/Math.min(width,height);

        Matrix matrix = new Matrix();
        matrix.setScale(scale,scale);
        shader.setLocalMatrix(matrix);

        //canvas.drawCircle(height/2,height/2,height/2,paint);

        //canvas.drawBitmap(bitmap,matrix,paint);

       // canvas.drawOval(new RectF(0,0,width+200,height+300),paint);

        //canvas.drawRect(new RectF(0,0,1000,1200),paint);

        //通过ShapeDrawable也可以实现
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setShader(shader);
        shapeDrawable.setBounds(0,0,width+200,height+300);
       // shapeDrawable.draw(canvas);


        RadialGradient radialGradient = new RadialGradient(300,300,100,
                colors,new float[]{0.2f,0.5f,1.0f}, Shader.TileMode.MIRROR);
        paint.setShader(radialGradient);
        //canvas.drawCircle(300,300,300,paint);


        //梯度渲染----扫描
        /**
         *
          cx	渲染中心点x 坐标
          cy	渲染中心y 点坐标
          colors	围绕中心渲染的颜色数组，至少要有两种颜色值
          positions	相对位置的颜色数组,可为null,  若为null,可为null,颜色沿渐变线均匀分布

         */
        SweepGradient sweepGradient = new SweepGradient(300,300,colors,new float[]{0,0.5f,1.0f});
        paint.setShader(sweepGradient);
       // canvas.drawCircle(300,300,300,paint);
       // canvas.drawArc(new RectF(0,0,300,300),0-phase,360+phase,true,paint);

        if(phase <= -359){

            phase = 0;
        }else{
            phase--;
        }
        Log.i("mahao",phase+"//////");
        // postInvalidateDelayed(50);


        LinearGradient linearGradient = new LinearGradient(0,0,300,300,new int[]{Color.GREEN
                ,Color.BLUE,Color.RED},null,Shader.TileMode.REPEAT);


        //梯度渲染和线性渲染组合模式；
        ComposeShader composeShader = new ComposeShader(shader,sweepGradient, PorterDuff.Mode.OVERLAY);
        paint.setShader(composeShader);
        canvas.drawRect(0,0,800,1000,paint);


    }
}













