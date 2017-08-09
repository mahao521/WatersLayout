package com.mahao.waterlayoutdemo;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mahao.waterlayoutdemo.widget.CircleProgressBar;

public class SecondActivity extends AppCompatActivity {


      public Object object = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        final CircleProgressBar circleBar = (CircleProgressBar) findViewById(R.id.circle_bar);

            circleBar.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            updateProgress(circleBar);
                        }
                    });
                    thread.start();
                }


            });

        }


    /**
     *
     * @param cirBar  设置线程要执行的任务
     */
    public synchronized  void updateProgress(CircleProgressBar cirBar){


        for (int i = 0; i <= 100; i++) {

            Log.i("mahao",i+"....\n");
            try {
                Thread.sleep(200);
                cirBar.setProgress(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }


}
