package com.demo.downloadtools.interfaces;

/**
 * @author wzj
 * @time 2018/3/26 14:19
 * @des ${TODO}
 */

public interface OnDownloadListener {
    void downloadError();
    void downloadSuccess();
    void downloadProgress(float totalsize, float progress);
}
