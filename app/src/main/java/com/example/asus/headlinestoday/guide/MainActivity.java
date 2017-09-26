package com.example.asus.headlinestoday.guide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.asus.headlinestoday.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Banner banner;
    private SharedPreferences flage;
    private boolean fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       flage =  getSharedPreferences("flage", MODE_PRIVATE);
        fl =  flage.getBoolean("fl", true);
        banner = (Banner) findViewById(R.id.banner);


        if (fl==false){
            Intent intent = new Intent(MainActivity.this, Yindao.class);
            startActivity(intent);
        }
        date();


    }

    private void date() {
        ArrayList<Integer> ints = new ArrayList<>();
        ints.add(R.drawable.aa);
        ints.add(R.drawable.bb);
        ints.add(R.drawable.cc);
        banner.setDelayTime(2000);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImages(ints);
        banner.isAutoPlay(true);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        banner.start();

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(5000);

                    if (fl){
                        SharedPreferences.Editor edit = flage.edit();
                        edit.putBoolean("fl",false).commit();
                        Intent intent = new Intent(MainActivity.this, Yindao.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
            }
            }
        }.start();

    }
}
