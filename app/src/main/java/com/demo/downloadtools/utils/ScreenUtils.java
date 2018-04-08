package com.demo.downloadtools.utils;


import com.demo.downloadtools.DownloadApplication;

/**
 * @author wzj
 * @time 2018/1/10 11:02
 * @des ${TODO}
 */

public class ScreenUtils {
    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight() {
        return DownloadApplication.getInstance().getApplicationContext().getResources()
                .getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth() {
        return DownloadApplication.getInstance().getApplicationContext().getResources()
                .getDisplayMetrics().widthPixels;
    }

    public static int dp2px(float dp) {
        //        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
        // ShareSettingsApplication.getInstance().getApplicationContext().getResources()
        // .getDisplayMetrics()) + 0.5f);
        final float scale = DownloadApplication.getInstance().getResources().getDisplayMetrics()
                .density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(float pxValue) {
        final float fontScale = DownloadApplication.getInstance().getApplicationContext()
                .getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = DownloadApplication.getInstance().getApplicationContext()
                .getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
