package com.zhexenov.weather.data;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

public class Converters {
    @TypeConverter
    public static Weather.Main fromString(String value) {
        return new Gson().fromJson(value, Weather.Main.class);
    }

    @TypeConverter
    public static String fromMain(Weather.Main main) {
        return new Gson().toJson(main);
    }
}
