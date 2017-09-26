package com.example.asus.headlinestoday.wedview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.asus.headlinestoday.R;

/**
 * Created by asus on 2017/9/14.
 */

public class WebviewActiviti extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_item);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        WebView web = (WebView) findViewById(R.id.webview_web);
        web.loadUrl(url);
        web.setWebViewClient(new WebViewClient(){
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);  //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        settings.setLoadsImagesAutomatically(true);//支持自动加载图片

    }

}
