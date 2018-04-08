package com.demo.downloadtools.downloadapk;


import android.util.Log;

import com.demo.downloadtools.interfaces.OnDownloadListener;
import com.demo.downloadtools.utils.AppUtils;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.ResponseBody;

/**
 * @author wzj
 * @time 2018/3/26 11:27
 * @des 文件操作工具
 */

public class FileUtils {

    private boolean isCancelDownload=false;

    /**
     * 将responseBody 写入目标文件targetpath
     *
     * @param body
     * @param targetpath
     * @return
     */
    public boolean writeResponseBodyToDisk(ResponseBody body, String targetpath, OnDownloadListener downloadListener) {
        InputStream inputStream = null;
        RandomAccessFile raf = null;
        try {
            byte[] fileReader = new byte[1024 * 8];
            inputStream = body.byteStream();
            long fileSize = body.contentLength();
            raf = new RandomAccessFile(targetpath, "rw");
            raf.seek(0);
            int read;
            int totalSize = 0;
            while ((read = inputStream.read(fileReader)) != -1) {
                raf.write(fileReader, 0, read);
                totalSize += read;
                downloadListener.downloadProgress(fileSize,totalSize);
                if(isCancelDownload()){
                    return false;
                }
            }
            if (raf != null) {
                raf.close();
            }
            return true;
        } catch (Exception e) {
            Log.e("tagtag",e.toString());
            return false;
        } finally {
            AppUtils.closeCloseable(inputStream);
            AppUtils.closeCloseable(raf);
        }
    }

    public boolean isCancelDownload() {
        return isCancelDownload;
    }

    public void setCancelDownload(boolean cancelDownload) {
        isCancelDownload = cancelDownload;
    }

    /**
     * 将.temp改成.apk
     *
     * @param path
     * @return
     */
    public static String renameFile(String path) {
        File file = new File(path);
        String newPath = path.substring(0, path.length() - 4) + "apk";
        File newFile = new File(newPath);
        file.renameTo(newFile);
        return newPath;
    }

    /**
     * 创建目录
     *
     * @param dirs
     */
    public static void createDirs(String dirs) {
        if (dirs == null) {
            return;
        }
        File dir = new File(dirs);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 删除指定目录中文件
     */
    public static void deleteFiles(final String dirs) {
        ThreadPoolUtils.getService().execute(new Runnable() {
            @Override
            public void run() {
                if (dirs != null) {
                    File file = new File(dirs);
                    if (file.isDirectory()) {
                        File[] files = file.listFiles();
                        for (File subfile : files) {
                            if (subfile.exists() && subfile.isFile()) {
                                subfile.delete();
                            }
                        }
                    }
                }
            }
        });
    }
}
