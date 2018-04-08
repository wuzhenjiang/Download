package com.demo.downloadtools.downloadapk;

import android.text.TextUtils;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络服务
 */
public class HttpUtils {
    private static HttpService service;
    public static HttpService getHttpService(){
        if(service==null){
            initService();
        }
        return service;
    }
    private static OkHttpClient client ;
    public static void initService(){
        TrustManager[] trustManager = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws
                            CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws
                            CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManager, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            client = new OkHttpClient().newBuilder().sslSocketFactory(sslSocketFactory).hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    if(TextUtils.isEmpty(hostname)||TextUtils.isEmpty(session.getPeerHost())){
                        return false;
                    }

                    return hostname.equals(session.getPeerHost());

                }
            }).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://baidu.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(HttpService.class);
    }
}
