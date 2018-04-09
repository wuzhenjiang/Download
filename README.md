# Download
apk download 、 FileProvider 、6.0permissions  支持8.0有进度条的apk下载
## 代码使用

1.通过url下载
```Java
DownLoadTool downLoadTool = new DownLoadTool();
downLoadTool.downloadApk(url, new OnDownloadListener() {
    @Override
    public void downloadError() {
       //错误回调 异步
    }

    @Override
    public void downloadSuccess() {
        //完成回调 异步
    }

    @Override
    public void downloadProgress(final float totalsize, final float progress) {
        //下载进度回调 异步
    }
});
DownloadManager.getInstance().addDownLoadTool(url, downLoadTool);
```
2.取消下载
```Java
DownLoadTool downLoadTool = DownloadManager.getInstance().getDownLoadTool(url);
if (downLoadTool != null) {
    downLoadTool.cancelDownload();
}
```

## 下载图片示例图<br> 

![image](https://github.com/wuzhenjiang/Download/raw/develop/app/src/main/res/drawable/download_icon_meitu_2.jpg)

