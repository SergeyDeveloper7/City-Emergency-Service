package com.sergeydeveloper7.cityemergencyservice.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sergeydeveloper7.data.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Retrofit singleton class
 */

public class RestClient {

    private static final String BASE_URL = BuildConfig.BASE_ENDPOINT;
    private RestInterface apiService;

    public RestClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.writeTimeout(60, TimeUnit.SECONDS);

        Gson gson = new GsonBuilder().create();
        OkHttpClient client = httpClient.build();
        Retrofit restAdapter = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(client)
                .baseUrl(BASE_URL)
                .build();
        apiService = restAdapter.create(RestInterface.class);
    }

    public RestInterface getApiService() {
        return apiService;
    }

}
