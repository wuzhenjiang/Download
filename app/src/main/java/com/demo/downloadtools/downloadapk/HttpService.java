package com.demo.downloadtools.downloadapk;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface HttpService {

    @Streaming
    @GET
    Observable<ResponseBody> downloadRange(@Url String url);
}

