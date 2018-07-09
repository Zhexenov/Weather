package com.zhexenov.weather.data;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

public class Converters {
    @TypeConverter
    public static WeatherDto.Main fromString(String value) {
        return new Gson().fromJson(value, WeatherDto.Main.class);
    }

    @TypeConverter
    public static String fromMain(WeatherDto.Main main) {
        return new Gson().toJson(main);
    }
}
