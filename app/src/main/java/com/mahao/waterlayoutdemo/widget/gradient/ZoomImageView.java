package com.mahao.waterlayoutdemo.widget.gradient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mahao.waterlayoutdemo.R;

/**
 * Created by mahao on 2017/8/9.
 */


/**
 *   原理： canvas上面画一个drawable;
 */

public class ZoomImageView extends View {


    //放大的倍数
    private int scale = 2;
    private Bitmap bitmap;

    private int radius = 100;

    private Matrix matrix;

    //画有形状的Drawable
    private ShapeDrawable shapeDrawable;

    public ZoomImageView(Context context) {
        this(context,null);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initData(context);
    }



    private void initData(Context context) {


        //传递一张Bitmap
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.rion);

        //放大后的图片
        Bitmap scaleBitmap = bitmap;

        //Creates a new bitmap, scaled from an existing bitmap
        scaleBitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()*scale,
                bitmap.getHeight()*scale,true);

        //放大后整个图片
        BitmapShader shader = new BitmapShader(scaleBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);


        //圆形的图片，覆盖在大图之上
        //Creates a ShapeDrawable with a specified Shape. subclass ---> pathShape,RectShape;
        shapeDrawable = new ShapeDrawable(new OvalShape());
        //the new shader to be installed in the paint
        /*
          After that any object (other than a bitmap) that is
         * drawn with that paint will get its color(s) from the shader.
         */
        shapeDrawable.getPaint().setShader(shader);

        //切除矩形区域
        shapeDrawable.setBounds(0,0,radius*2,radius*2);

        matrix = new Matrix();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawBitmap(bitmap,0,0,null);

        //绘制放大镜的图
        //onDraw(state.mShape, canvas, paint); ----> 调用shape.draw方法；
        shapeDrawable.draw(canvas);



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        int x = (int) event.getX();
        int y = (int) event.getY();


        //平移矩阵--画布上的drawshape反方向平移
        matrix.setTranslate(radius - x*scale,radius-y*scale);

        // Set the shader's local matrix ； Shader着色器
        shapeDrawable.getPaint().getShader().setLocalMatrix(matrix);


        //切割出手势区域位置的圆
        shapeDrawable.setBounds(x-radius,y-radius,x+radius,y+radius);

         invalidate();
         return true;

    }
}
