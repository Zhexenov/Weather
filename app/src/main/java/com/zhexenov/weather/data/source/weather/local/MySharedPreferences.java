package com.zhexenov.weather.data.source.weather.local;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class MySharedPreferences {

    public static final String LAST_REQUEST = "last_success_request_key";

    private SharedPreferences mSharedPreferences;

    @Inject
    MySharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public void putData(String key, String data) {
        mSharedPreferences.edit().putString(key, data).apply();
    }

    public String getData(String key) {
        return mSharedPreferences.getString(key, null);
    }
}
