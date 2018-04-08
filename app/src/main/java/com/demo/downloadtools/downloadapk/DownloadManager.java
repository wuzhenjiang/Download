package com.demo.downloadtools.downloadapk;

import java.util.HashMap;

/**
 * @author wzj
 * @time 2018/3/26 11:57
 * @des 下载管理
 */

public class DownloadManager {
    private HashMap<String, DownLoadTool> mDownLoadToolHashMap=new HashMap<>();
    private ThreadLocal<String> mThreadLocal=new ThreadLocal<>();

    private DownloadManager(){}

    public static DownloadManager getInstance(){
        return SingleFactory.downloadManager;
    }

    private static class SingleFactory{
       private static DownloadManager downloadManager=new DownloadManager();
    }



    public void addDownLoadTool(String url, DownLoadTool downloadtool){
       mDownLoadToolHashMap.put(url,downloadtool);
    }

    public void deleteDownLoadTool(String url){
        if(mDownLoadToolHashMap.containsKey(url)){
            mDownLoadToolHashMap.remove(url);
        }
    }

    public boolean isDownloading(String url){
        if(mDownLoadToolHashMap.containsKey(url)){
            return true;
        }else{
            return false;
        }
    }

    public DownLoadTool getDownLoadTool(String url){
        return mDownLoadToolHashMap.get(url);
    }

    public void setThreadUrl(String url){
        mThreadLocal.set(url);
    }

    public String getThreadUrl(){
        return mThreadLocal.get();
    }

    public void removeThreadUrl(){
        mThreadLocal.remove();
    }
}
