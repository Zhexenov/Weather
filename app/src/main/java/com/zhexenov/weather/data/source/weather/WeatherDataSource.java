package com.zhexenov.weather.data.source.weather;

import android.support.annotation.NonNull;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.Weather;

import io.reactivex.Maybe;
import io.reactivex.Single;

public interface WeatherDataSource {

    Single<Weather> getSingleWeather(int cityId, boolean onlyValidCache);

    void saveWeather(@NonNull Weather forecast);

    void deleteWeather(@NonNull Weather forecast);

    boolean validWeatherExists(int cityId, boolean validateCache);
}
