package com.zhexenov.weather.data.source.weather.remote;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.zhexenov.weather.api.WeatherApi;
import com.zhexenov.weather.data.Weather;
import com.zhexenov.weather.data.WeatherDto;
import com.zhexenov.weather.data.source.weather.WeatherDataSource;

import javax.inject.Inject;

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
        Timber.e("remote data source");
        api.fetchWeather(cityId, "16f357cca642e295922954dfe882053f", "metric")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherDto>() {
                    @Override
                    public void accept(WeatherDto forecast) {
                        Weather weather = new Weather(forecast.getCityId(), forecast.getDateTime(), forecast.getMain().getTemp());
                        callback.onWeatherLoaded(weather);
                    }
                });
    }

    @Override
    public Weather loadWeatherForCity(int cityId) {
        return null;
    }

    @Override
    public void saveWeather(@NonNull Weather forecast) {

    }

    @Override
    public void deleteWeather(@NonNull Weather forecast) {

    }
}
