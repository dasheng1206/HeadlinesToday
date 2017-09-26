package com.example.asus.headlinestoday.httputils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by asus on 2017/9/11.
 */

public class Httputies {
    //在activity中调用此方法，请求数据，并获取返回的数据；
    public void getDataFromServer(Context context, RequestBean bean, DataCallBack callBack){
        MyHandler handler=new MyHandler(context,callBack);
        MyTask task=new MyTask(bean,handler);
        //获取CPU数量
        int cpunum = Runtime.getRuntime().availableProcessors();
        //线程池实例化
        ExecutorService service = Executors.newScheduledThreadPool(cpunum + 1);
        //将子线程放入线程池执行；
        service.execute(task);
    }

    /**
     * 因为网络请求是耗时操作所以需要在线程里进行
     * 这里创建一个内部类 继承Thread  重写run方法
     */
    class MyTask extends Thread{
        private RequestBean requestBean;
        private MyHandler handler;

        public MyTask(RequestBean requestBean, MyHandler handler) {
            this.requestBean = requestBean;
            this.handler = handler;
        }

        @Override
        public void run() {
            super.run();
            try {
                URL url=new URL(requestBean.url);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                //判断是什么请求方式
                if(requestBean.method.equals("POST")){
                    connection.setRequestMethod(requestBean.method);
                    connection.setDoOutput(true);
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(requestBean.value.getBytes());
                }
                StringBuilder builder=new StringBuilder();
                int code = connection.getResponseCode();
                if(code==HttpURLConnection.HTTP_OK){
                    InputStream is = connection.getInputStream();
                    String str="";
                    BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                    while ((str=reader.readLine())!=null){
                        builder.append(str);
                    }
                }
                //利用
                Message message= Message.obtain();
                message.obj=builder.toString();
                message.what=code;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 请求的数据哟啊通过Handler接收
     */
    class MyHandler extends Handler {
        private Context context;
        private DataCallBack callBack;

        public MyHandler(Context context, DataCallBack callBack) {
            this.context = context;
            this.callBack = callBack;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if(what==200){
                String json= (String) msg.obj;
                callBack.prosseData(json);
            } else {
                Toast.makeText(context, "请求失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public abstract interface DataCallBack{
        public abstract void prosseData(String json);
    }
}

