package com.demo.downloadtools.activity;

import android.Manifest;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.demo.downloadtools.R;
import com.demo.downloadtools.downloadapk.DownLoadTool;
import com.demo.downloadtools.downloadapk.DownloadManager;
import com.demo.downloadtools.downloadapk.ThreadPoolUtils;
import com.demo.downloadtools.interfaces.OnDownloadListener;
import com.demo.downloadtools.utils.AppUtils;
import com.demo.downloadtools.widget.CustomProgressBar;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

/**
 * @author wzj
 * @time 2018/4/8 14:20
 * @des 下载apk
 */

public class DownloadApkActivity extends Activity {
    RxPermissions rxPermissions ;
    private static final String TOTALSIZE = "totalsize";
    private static final String PROGRESS = "progress";
    @BindView(R.id.btn_confirm)
    CustomProgressBar mCustomProgressBar;

    private String url = "http://gdown.baidu" +
            ".com/data/wisegame/e272ea7cbaae2480/yingyongbao_7182130.apk";
    private Unbinder mUnbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_apk);
        mUnbinder = ButterKnife.bind(this);
        rxPermissions = new RxPermissions(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @OnClick({R.id.bt_download, R.id.bt_cancel})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.bt_download://下载
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {
                        downloadApk(url);
                    }
                });
                break;
            case R.id.bt_cancel://取消
                cancelDownload(url);
                break;
        }
    }

    /**
     * 取消下载
     *
     * @param url
     */
    private void cancelDownload(String url) {
        DownLoadTool downLoadTool = DownloadManager.getInstance().getDownLoadTool(url);
        if (downLoadTool != null) {
            downLoadTool.cancelDownload();
        }
    }

    /**
     * 下载
     *
     * @param url
     */
    private void downloadApk(final String url) {
        final Bundle bundle = new Bundle();
        final DownLoadTool downLoadTool = new DownLoadTool();
        ThreadPoolUtils.getService().execute(new Runnable() {
            @Override
            public void run() {
                downLoadTool.downloadApk(url, new OnDownloadListener() {
                    @Override
                    public void downloadError() {
                        mHandler.sendEmptyMessage(3);
                    }

                    @Override
                    public void downloadSuccess() {
                        mHandler.sendEmptyMessage(1);
                    }

                    @Override
                    public void downloadProgress(final float totalsize, final float progress) {
                        Message message = Message.obtain();
                        message.what = 2;
                        bundle.putFloat(TOTALSIZE, totalsize);
                        bundle.putFloat(PROGRESS, progress);
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                    }
                });
            }
        });
        DownloadManager.getInstance().addDownLoadTool(url, downLoadTool);
    }


    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    setDownloadProgress(DownloadApkActivity.this, mCustomProgressBar, 1, 1000,
                            "更新进度");
                    break;
                case 2:
                    Bundle data = msg.getData();
                    float totalsize = data.getFloat(TOTALSIZE);
                    float progress = data.getFloat(PROGRESS);
                    if (mCustomProgressBar != null) {
                        setDownloadProgress(DownloadApkActivity.this, mCustomProgressBar, 2,
                                (int) ((progress / totalsize) * 1000), AppUtils.formatMB
                                        (progress) + "/" + AppUtils.formatMB(totalsize));
                    } else {
                        mCustomProgressBar = (CustomProgressBar) findViewById(R.id.btn_confirm);
                    }
                    break;
                case 3:
                    setDownloadProgress(DownloadApkActivity.this, mCustomProgressBar, 1, 1000,
                            "更新进度");
                    Toast.makeText(DownloadApkActivity.this,"下载失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    /**
     * @param act
     * @param downloadProgressBar
     * @param downloadstatus      1初始状态 2下载状态
     * @param progress            进度
     */
    public static void setDownloadProgress(Activity act, CustomProgressBar downloadProgressBar, int
            downloadstatus, int progress, String text) {
        if (downloadstatus == 1) {
            downloadProgressBar.setTextColor(Color.WHITE);
            downloadProgressBar.setText(text);
            downloadProgressBar.setProgress(1000);
            downloadProgressBar.setProgressDrawable(act.getResources().getDrawable(R.drawable
                    .purple_btn_bg));
        } else if (downloadstatus == 2) {
            downloadProgressBar.setTextColor(Color.WHITE);
            downloadProgressBar.setText(text);
            downloadProgressBar.setProgressDrawable(act.getResources().getDrawable(R.drawable
                    .rewards_info_download_progressbar));
            downloadProgressBar.setProgress(progress);
        }
    }
}
