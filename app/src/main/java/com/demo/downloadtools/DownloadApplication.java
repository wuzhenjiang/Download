package com.demo.downloadtools;

import android.app.Application;


/**
 * @author wzj
 * @time 2018/4/8 14:26
 * @des
 */

public class DownloadApplication extends Application {
    private static DownloadApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        DownloadApplication.instance = this;
    }


    public static DownloadApplication getInstance() {
        return DownloadApplication.instance;
    }
}
