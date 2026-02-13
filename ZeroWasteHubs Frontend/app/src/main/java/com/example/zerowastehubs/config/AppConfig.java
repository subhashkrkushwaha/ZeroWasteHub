package com.example.zerowastehubs.config;

import android.content.Context;

import com.example.zerowastehubs.R;

public class AppConfig {
    public static String getBaseUrl(Context context) {
        return context.getString(R.string.ipConfig);
    }
}
