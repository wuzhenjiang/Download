package com.demo.downloadtools.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.demo.downloadtools.DownloadApplication;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ljr on 2018/1/22.
 */

public class AppUtils {

    /**
     * 安装应用
     * @param url
     */
    public static void install(String url) {
        // 核心是下面几句代码
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(url)),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        DownloadApplication.getInstance().startActivity(intent);
    }

    /**
     * MD5加密
     *
     * @param string
     * @return
     */
    public static String MD5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 关闭Closeable
     * @param closeable
     */
    public static void closeCloseable(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化成MB
     * @param size
     * @return
     */
    public static String formatMB(float size){
        if(size<=0){
            return "0MB";
        }else{
            return String.format("%.1f", size* 1f / 1024 / 1024) + "MB";
        }
    }

    public static void installApk(String apkPath) {
        if (DownloadApplication.getInstance() == null || TextUtils.isEmpty(apkPath)) {
            return;
        }


        File file = new File(apkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);

        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(DownloadApplication.getInstance(), "com.mydomain.fileprovider", file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }

        DownloadApplication.getInstance().startActivity(intent);
    }
}
