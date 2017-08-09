package com.mahao.waterlayoutdemo.widget.gradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.media.DrmInitData;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by mahao on 17/8/3.
 */

public class LinerGradientText extends AppCompatTextView {


    private TextPaint textPaint;
    private Matrix matrix;

    private LinearGradient linearGradient;


    private int stepLength = 20;
    private int tranlate;

    public LinerGradientText(Context context) {
        this(context,null);
    }

    public LinerGradientText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LinerGradientText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initData();
    }


    private void initData() {


        textPaint = getPaint();
        textPaint.setColor(Color.RED);
        matrix = new Matrix();

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        String text = getText().toString();
        float txtLength = textPaint.measureText(text);

        //显示三个
        int showLength = (int) (txtLength/text.length()*3);

        Log.i("mahao",getWidth()+"........."+showLength);

        /**
         *    第一，二个参数 ： X起始位 Y起始位置
         *    第三，四个参数 ： X的结束为止，Y的结束位置
         *
         *    int[] 渐变颜色数组
         *
         *    float[] 渐变颜色的相对位置，null表示均匀分布
         *
         */
        linearGradient = new LinearGradient(-showLength,0,0,0,new int[]{0x22ffffff,0xffffffff,0x22ffffff},
                new float[]{0,0.5f,1.0f}, Shader.TileMode.CLAMP);

        textPaint.setShader(linearGradient);
    }

   /* @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        String text = getText().toString();
        float txtLength = textPaint.measureText(text);

        //显示三个
        int showLength = (int) (txtLength/text.length()*3);

        Log.i("mahao",getWidth()+"........."+showLength);
        linearGradient = new LinearGradient(-showLength,0,0,0,new int[]{0x22ffffff,0xfffffff,0x33ffffff},
                null, Shader.TileMode.CLAMP);

        textPaint.setShader(linearGradient);
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        tranlate += stepLength;

        float stringWIdth = getPaint().measureText(getText().toString());

        if(tranlate < 1 || tranlate > stringWIdth){

            stepLength = -stepLength;
        }
        Log.i("mahao","tranlate---"+tranlate);
        matrix = new Matrix();
        matrix.setTranslate(tranlate,0);
        linearGradient.setLocalMatrix(matrix);
        postInvalidateDelayed(50);
    }
}
























