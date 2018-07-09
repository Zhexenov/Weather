package com.zhexenov.weather.data.source.weather.remote;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.zhexenov.weather.api.WeatherApi;
import com.zhexenov.weather.data.Weather;
import com.zhexenov.weather.data.WeatherDto;
import com.zhexenov.weather.data.source.weather.WeatherDataSource;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class WeatherRemoteDataSource implements WeatherDataSource {

    @Inject
    WeatherApi api;

    @Inject
    WeatherRemoteDataSource() {
    }

    @SuppressLint("CheckResult")
    @Override
    public void getWeatherForCity(int cityId, @NonNull final GetWeatherCallback callback) {
        api.fetchWeather(cityId, "16f357cca642e295922954dfe882053f", "metric")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(forecast -> {
                    Weather weather = new Weather(forecast.getCityId(), forecast.getDateTime(), forecast.getMain().getTemp());
                    callback.onWeatherLoaded(weather);
                });
    }


    @Override
    public void saveWeather(@NonNull Weather forecast) {

    }

    @Override
    public void deleteWeather(@NonNull Weather forecast) {

    }
}
