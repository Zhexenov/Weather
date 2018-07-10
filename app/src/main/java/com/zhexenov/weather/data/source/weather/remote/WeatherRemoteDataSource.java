package com.zhexenov.weather.data.source.weather.remote;

import android.support.annotation.NonNull;

import com.zhexenov.weather.api.WeatherApi;
import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.Weather;
import com.zhexenov.weather.data.source.weather.WeatherDataSource;

import javax.inject.Inject;

import io.reactivex.Single;

public class WeatherRemoteDataSource implements WeatherDataSource {

    @Inject
    WeatherApi api;

    @Inject
    WeatherRemoteDataSource() {
    }

    @Override
    public Single<Weather> getSingleWeather(int cityId, boolean onlyValidCache) {
        return api.fetchWeather(cityId, "16f357cca642e295922954dfe882053f", "metric");
    }


    @Override
    public void saveWeather(@NonNull Weather forecast) {

    }

    @Override
    public void deleteWeather(@NonNull Weather forecast) {

    }

    @Override
    public boolean validWeatherExists(int city, boolean validateCache) {
        return false;
    }
}
