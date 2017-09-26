package com.example.asus.headlinestoday.guide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.headlinestoday.R;
import com.example.asus.headlinestoday.ZhuActivity;

/**
 * Created by asus on 2017/9/7.
 */

public class Yindao extends AppCompatActivity {



    private TextView tv;
    int nanber = 2;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (nanber > 0) {
                nanber--;
                tv.setText("广告剩于" + nanber + "秒");
                handler.sendEmptyMessageDelayed(0, 1000);
            }else if (nanber==0){
                Intent intent = new Intent(Yindao.this, ZhuActivity.class);
                startActivity(intent);
                handler.removeMessages(0);
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa);
        Button bt = (Button) findViewById(R.id.bt_bt);
        tv = (TextView) findViewById(R.id.tv_tv);
        handler.sendEmptyMessageDelayed(0, 1000);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Yindao.this, ZhuActivity.class);
                startActivity(intent);
                handler.removeMessages(0);

            }
        });
    }
}
