package com.dorothy.hacknews;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;

/**
 * Created by dorothy on 2017/3/18.
 */

public class HackNewsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AVObject.registerSubclass(News.class);
        AVOSCloud.initialize(this,"lHAuWyMaYa7b50pTEkvRnhMl-gzGzoHsz","eruDyMS8s5OW4VvGAd3GFYGw");
        AVOSCloud.setDebugLogEnabled(true);
    }
}
