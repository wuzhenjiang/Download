package com.demo.downloadtools.downloadapk;


import com.demo.downloadtools.interfaces.OnDownloadListener;
import com.demo.downloadtools.utils.AppUtils;
import com.demo.downloadtools.utils.Constants;

import java.io.File;

import io.reactivex.schedulers.Schedulers;

/**
 * @author wzj
 * @time 2018/3/26 11:51
 * @des apk下载工具
 */

public class DownLoadTool {

    private DownloadObserver mDownloadObserver;

    /**
     * 下载应用
     * @param url
     */
    public void downloadApk(final String url,OnDownloadListener ondownloadlistener){
        DownloadManager.getInstance().setThreadUrl(url);
        String apkName = AppUtils.MD5(url) + ".temp";
        String path = Constants.APK_DIRS+ File.separator+
                apkName;//外部存储
        FileUtils.createDirs(Constants.APK_DIRS);
        mDownloadObserver=new DownloadObserver(path,ondownloadlistener);
        HttpUtils.getHttpService().downloadRange(url).subscribeOn(Schedulers.io()).observeOn
                (Schedulers.io()).subscribe(mDownloadObserver);
    }

    public void cancelDownload(){
        if(mDownloadObserver!=null){
            mDownloadObserver.cancelDownload();
        }
    }

}
