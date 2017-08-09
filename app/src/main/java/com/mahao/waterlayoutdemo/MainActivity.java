package com.mahao.waterlayoutdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mahao.waterlayoutdemo.widget.WaterLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements WaterLayout.ClickViewListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       final WaterLayout layout = (WaterLayout) findViewById(R.id.layout_water);
       findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {

               addView(layout);
           }
       });

    }

    /**
     *
     * @param layout  向容器内添加数据
     */
    public void addView(WaterLayout layout){


        Random random = new Random();
        int height = random.nextInt(400);

       WaterLayout.WaterLayoutParams layoutParams = new WaterLayout.WaterLayoutParams(
                WaterLayout.WaterLayoutParams.MATCH_PARENT,
                height
        );

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher);
     //   ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
     //   imageView.setLayoutParams(layoutParams);
        layout.addView(imageView,layoutParams);

        layout.setOnItemClickListener(this);

    }

    @Override
    public void clickView(int position, View view) {


        if(position % 5 == 0){

            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(intent);

        }else if(position % 5 == 1){

            Intent intent = new Intent(MainActivity.this,DashActivity.class);
            startActivity(intent);

        }else if(position % 5 == 2){

            Intent intent = new Intent(this,ThridActivity.class);
            startActivity(intent);

        }else if(position % 6 == 3){

            Intent intent = new Intent(this,FourActivity.class);
            startActivity(intent);
        }else if(position %6 == 4){


            Intent intent = new Intent(this,FiveActivity.class);
            startActivity(intent);
        }

    }
}
