package com.zhexenov.weather.data.source.weather.local;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.zhexenov.weather.data.Weather;
import com.zhexenov.weather.data.source.weather.WeatherDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@Singleton
public class WeatherLocalDataSource implements WeatherDataSource {

    private final WeathersDao weathersDao;

    @Inject
    WeatherLocalDataSource(WeathersDao weathersDao) {
        this.weathersDao = weathersDao;
    }

    @Override
    public void getWeatherForCity(int cityId, boolean onlyValidCache, @NonNull final GetWeatherCallback callback) {
        weathersDao.getWeatherForCityId(cityId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Weather>() {
                    @Override
                    public void onSuccess(Weather weather) {
                        callback.onWeatherLoaded(weather);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onDataNotAvailable();
                    }
                });
    }
    

    @Override
    public void saveWeather(@NonNull Weather forecast) {
        Completable.fromAction(() -> weathersDao.insertWeather(forecast)).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void deleteWeather(@NonNull Weather forecast) {
        Completable.fromAction(() -> weathersDao.deleteWeather(forecast)).subscribeOn(Schedulers.io()).subscribe();
    }
}
