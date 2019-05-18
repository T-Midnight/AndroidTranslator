package com.androidtranslator.web;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static final String ROOT = "https://translate.yandex.net";
    private static Retrofit INSTANCE;

    private static synchronized Retrofit getRetrofitInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Retrofit.Builder()
                    .baseUrl(ROOT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return INSTANCE;
    }

    public static Api getApi() {
        return getRetrofitInstance().create(Api.class);
    }
}
