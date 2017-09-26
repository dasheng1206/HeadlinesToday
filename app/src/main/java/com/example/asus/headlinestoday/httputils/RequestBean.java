package com.example.asus.headlinestoday.httputils;

/**
 * Created by asus on 2017/9/11.
 */

public class RequestBean {
    public String url="";
    public String value="";
    //默认为GET请求
    public String method="GET";
    //get请求
    public RequestBean(String url) {
        this.url = url;
    }
    //post请求
    public RequestBean(String url, String value, String method) {
        this.url = url;
        this.value = value;
        this.method = method;
    }
}

