package com.zhexenov.weather.data.source.weather.local;

import android.support.annotation.NonNull;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.Weather;
import com.zhexenov.weather.data.source.weather.WeatherDataSource;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class WeatherLocalDataSource implements WeatherDataSource {

    private final WeathersDao weathersDao;

    @Inject
    WeatherLocalDataSource(WeathersDao weathersDao) {
        this.weathersDao = weathersDao;
    }

    @Override
    public Single<Weather> getSingleWeather(int cityId, boolean onlyValidCache) {
        return weathersDao.getWeatherForCityId(cityId);
    }

    @Override
    public void saveWeather(@NonNull Weather forecast) {
        Completable.fromAction(() -> weathersDao.insertWeather(forecast)).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void deleteWeather(@NonNull Weather forecast) {
        Completable.fromAction(() -> weathersDao.deleteWeather(forecast)).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public boolean validWeatherExists(int cityId, boolean validateCache) {
        final Weather weather = weathersDao.weatherForCityId(cityId);

        if (weather == null) {
            return false;
        }
        if (validateCache) {
            long seconds = Calendar.getInstance().getTimeInMillis() / 1000;
            return weather.getDateTime() + 3600 > seconds;
        }
        return true;
    }
}
