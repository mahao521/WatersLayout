package com.mahao.waterlayoutdemo.widget;

import android.content.Context;
import android.icu.text.TimeZoneNames;
import android.service.carrier.CarrierService;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by mahao on 17/7/24.
 */

public class WaterLayout extends ViewGroup {

    //每个item的宽度
    private int childWidth;

    //有多少列
    private int column;

    //水平列间距
    private int horizontailSpace;


    //竖直行间距
    private int verticalSpace;


    //定义一个column列的数组-记录没个行高
    private int[] top;


    public WaterLayout(Context context) {
        this(context,null);
    }

    public WaterLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initData();

    }


    private void initData() {


        column = 2;
        horizontailSpace = 10;
        verticalSpace = 10;

        top = new int[column];
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int totalWidth = 0;
        int totolHeight = 0;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int hightSize = MeasureSpec.getSize(heightMeasureSpec);
        int hightMode = MeasureSpec.getMode(heightMeasureSpec);



        //计算宽度
        switch (widthMode){

            case  MeasureSpec.EXACTLY:


                totalWidth = widthSize;

                break;

            case MeasureSpec.AT_MOST:

                int a = MeasureSpec.makeMeasureSpec(widthMeasureSpec,heightMeasureSpec);
                Log.i("mahao",a+".....");

                measureChildren(widthMeasureSpec,heightMeasureSpec);

                childWidth = (widthSize - (column -1)*horizontailSpace)/column;

                int childCount  = getChildCount();
                if(childCount < column){

                    totalWidth = childCount*childWidth + (childCount-1)*horizontailSpace;

                }else {

                    totalWidth = widthSize;
                }

                break;

            case MeasureSpec.UNSPECIFIED:



                break;

        }


        //计算高度
        switch (hightMode){

            case  MeasureSpec.EXACTLY:

                totolHeight = hightSize;
                break;

            case MeasureSpec.AT_MOST:

            case  MeasureSpec.UNSPECIFIED:


                MeasureSpec.makeMeasureSpec(widthMeasureSpec,heightMeasureSpec);

                measureChildren(widthMeasureSpec,heightMeasureSpec);

                int count = getChildCount();


                clearTop();
                for(int i = 0; i< count; i++){

                    int minTop = getMinTop();

                    View childAt = getChildAt(i);
                    int measuredHeight = childAt.getMeasuredHeight();


                    WaterLayoutParams layoutParams = (WaterLayoutParams) childAt.getLayoutParams();
                    layoutParams.left = minTop*childWidth + horizontailSpace *minTop;
                    layoutParams.right = layoutParams.left + childWidth;
                    layoutParams.top = top[minTop];
                    layoutParams.bottom = top[minTop] + measuredHeight;


                    //第一次赋值 top[0] top[1] top[2] top[3]
                    top[minTop] += measuredHeight + verticalSpace;

                }

                //获取最大的高度
                int maxTop = getMaxTop();
                totolHeight = maxTop;
                break;

        }
        Log.i("mahao",totalWidth +"...." + totolHeight);
        setMeasuredDimension(totalWidth,totolHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        clearTop();

        for(int i = 0; i < getChildCount(); i++){


            View childAt = getChildAt(i);
            WaterLayoutParams layoutParams = (WaterLayoutParams) childAt.getLayoutParams();

            childAt.layout(layoutParams.left,layoutParams.top,layoutParams.right,layoutParams.bottom);
            Log.i("mahao",layoutParams.left +"..." +layoutParams.top+"..."+layoutParams.right+"..."+layoutParams.bottom);

        }

    }


    /**
     *
     * @return   获取当前行最小的高度
     */
    public int getMinTop(){


        int index = 0;
        for(int i =0; i < column; i++){

            if(top[i] < top[index]){

                index = i;
            }
        }

        return index;

    }


    /**
     *    获取column中最大的高度
     * @return
     */
    public int  getMaxTop(){


        int temp = top[0];
        for(int i = 0; i < column; i++){

            if(top[i] > temp){


                temp = top[i];
            }
        }
        return temp;

    }


    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new WaterLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new WaterLayoutParams(WaterLayoutParams.WRAP_CONTENT,WaterLayoutParams.WRAP_CONTENT);
    }



    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof WaterLayoutParams;
    }

    public static class WaterLayoutParams extends ViewGroup.LayoutParams{


        private int left = 0;
        public  int top = 0;
        public  int right = 0;
        public int bottom = 0;


        public WaterLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public WaterLayoutParams(int width, int height) {
            super(width, height);
        }

        public WaterLayoutParams(LayoutParams source) {
            super(source);
        }
    }


    /**
     *   清除数据
     */
    public void clearTop(){


        for(int i = 0; i < column ; i++){


            top[i] = 0;
        }

    }


    /**
     *
     * @param listener  设置item监听
     */
    public void setOnItemClickListener(final ClickViewListener listener){

        for(int i = 0; i < getChildCount(); i++){

            View childAt = getChildAt(i);
            final int index = i;
            childAt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.clickView(index,v);
                }
            });
        }
    }

    /**
     *   回调点击事件
     */
    public interface  ClickViewListener{

        void clickView(int position,View  view);
    }

}
