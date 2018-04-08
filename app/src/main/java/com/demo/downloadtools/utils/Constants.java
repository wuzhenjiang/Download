package com.demo.downloadtools.utils;

import android.os.Environment;

import java.io.File;

/**
 * @author wzj
 * @time 2018/4/8 14:37
 * @des
 */

public class Constants {
    public static String APK_DIRS= Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"downloadtools";
}
