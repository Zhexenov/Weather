package com.zhexenov.weather.data.source.weather;

import android.support.annotation.NonNull;

import com.zhexenov.weather.data.Weather;
import com.zhexenov.weather.data.WeatherDto;

import java.util.Observable;

import io.reactivex.Flowable;

public interface WeatherDataSource {

    void getWeatherForCity(int cityId, @NonNull GetWeatherCallback callback);

    Weather loadWeatherForCity(int cityId);

    void saveWeather(@NonNull Weather forecast);

    void deleteWeather(@NonNull Weather forecast);

    interface GetWeatherCallback {

        void onWeatherLoaded(Weather forecast);

        void onDataNotAvailable();
    }

}
