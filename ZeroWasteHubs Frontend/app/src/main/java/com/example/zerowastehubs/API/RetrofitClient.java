package com.example.zerowastehubs.API;


import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;

import com.example.zerowastehubs.config.AppConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
//    private static final String BASE_URL = "http://10.140.110.169:8080/";

        public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConfig.getBaseUrl(context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
