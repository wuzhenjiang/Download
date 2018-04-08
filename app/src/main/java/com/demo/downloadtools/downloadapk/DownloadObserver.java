package com.demo.downloadtools.downloadapk;


import com.demo.downloadtools.interfaces.OnDownloadListener;
import com.demo.downloadtools.utils.AppUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;


/**
 * @author wzj
 * @time 2018/3/26 11:25
 * @des 下载监听
 */

public class DownloadObserver implements Observer<ResponseBody> {
    private String downloadPath;
    private boolean downSuccess = false;
    private OnDownloadListener mOnDownloadListener;
    private FileUtils mFileUtils;

    DownloadObserver(String downloadPath, OnDownloadListener downloadListener) {
        this.downloadPath = downloadPath;
        this.mOnDownloadListener=downloadListener;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull ResponseBody responseBody) {
        mFileUtils=new FileUtils();
        downSuccess = mFileUtils.writeResponseBodyToDisk(responseBody, downloadPath,mOnDownloadListener);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        downSuccess=false;
    }

    @Override
    public void onComplete() {
        String threadUrl = DownloadManager.getInstance().getThreadUrl();
        DownloadManager.getInstance().deleteDownLoadTool(threadUrl);
        DownloadManager.getInstance().removeThreadUrl();
        if (downSuccess) {
            String newPath = FileUtils.renameFile(downloadPath);
            AppUtils.installApk(newPath);
            mOnDownloadListener.downloadSuccess();
        }else{
            mOnDownloadListener.downloadError();
        }
    }

    public void cancelDownload(){
        if(mFileUtils!=null){
            mFileUtils.setCancelDownload(true);
        }
    }
}
