package com.zhexenov.weather.data.source.weather;

import android.support.annotation.NonNull;

import com.zhexenov.weather.data.Weather;

public interface WeatherDataSource {

    void getWeatherForCity(int cityId, @NonNull GetWeatherCallback callback);

    void saveWeather(@NonNull Weather forecast);

    void deleteWeather(@NonNull Weather forecast);

    interface GetWeatherCallback {

        void onWeatherLoaded(Weather forecast);

        void onDataNotAvailable();
    }
}
