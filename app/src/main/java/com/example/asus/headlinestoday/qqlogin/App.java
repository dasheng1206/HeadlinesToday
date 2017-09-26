package com.example.asus.headlinestoday.qqlogin;

import android.app.Application;

import com.mob.MobSDK;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by asus on 2017/9/7.
 */

public class App extends Application{
    {
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        MobSDK.init(this, this.a(), this.b());
    }
    protected String a() {
        return null;
    }

    protected String b() {
        return null;
    }


}
