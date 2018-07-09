package com.zhexenov.weather.data.source.weather;

import android.support.annotation.NonNull;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.Weather;
import com.zhexenov.weather.data.WeatherDto;

import java.util.Observable;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface WeatherDataSource {

    void getWeatherForCity(int cityId, boolean onlyValidCache, @NonNull GetWeatherCallback callback);

    void saveWeather(@NonNull Weather forecast);

    void deleteWeather(@NonNull Weather forecast);

    interface GetWeatherCallback {

        void onWeatherLoaded(Weather forecast);

        void onDataNotAvailable();
    }

    interface GetWeatherForCityCallback {

        void onWeatherLoaded(Weather forecast, City city);

        void onDataNotAvailable(City city);
    }

}
