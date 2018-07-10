package com.zhexenov.weather.data.source.weather;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zhexenov.weather.data.Weather;
import com.zhexenov.weather.data.source.Local;
import com.zhexenov.weather.data.source.Remote;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import timber.log.Timber;

public class WeatherRepository implements WeatherDataSource {

    private final WeatherDataSource localDataSource;
    private final WeatherDataSource remoteDataSource;

    private Map<Integer, Weather> cachedWeathers;

    @Inject
    WeatherRepository(@Local WeatherDataSource localDataSource,
                      @Remote WeatherDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }


    @Override
    public Single<Weather> getSingleWeather(int cityId, boolean onlyValidCache) {
        Weather weather = getWeatherForCity(cityId);
        if (weather != null) {
            if (isValidCache(weather)) {
                return Single.just(weather);
            }
            cachedWeathers.remove(cityId);
        }
        return Single.create((SingleOnSubscribe<Boolean>) e -> e.onSuccess(localDataSource.validWeatherExists(cityId, onlyValidCache)))
                .flatMap((Function<Boolean, SingleSource<Weather>>) aBoolean -> {
                    if (aBoolean) {
                        return localDataSource.getSingleWeather(cityId, onlyValidCache).map(weather1 -> {
                            cacheWeather(weather1);
                            return weather1;
                        });
                    }
                    return remoteDataSource.getSingleWeather(cityId, false).map(weather11 -> {
                        cacheWeather(weather11);
                        saveWeather(weather11);
                        return weather11;
                    });
                });
    }

    @Override
    public boolean validWeatherExists(int cityId, boolean validateCache) {
        return false;
    }

    @Override
    public void saveWeather(@NonNull Weather forecast) {
        localDataSource.saveWeather(forecast);
    }

    @Override
    public void deleteWeather(@NonNull Weather forecast) {
        localDataSource.deleteWeather(forecast);
    }

    private void cacheWeather(@NonNull Weather forecast) {
        if (cachedWeathers == null) {
            cachedWeathers = new LinkedHashMap<>();
        }
        cachedWeathers.put(forecast.getCityId(), forecast);
    }

    private boolean isValidCache(@NonNull Weather weather) {
        long seconds = Calendar.getInstance().getTimeInMillis() / 1000;
        return weather.getDateTime() + 3600 > seconds;
    }

    @Nullable
    private Weather getWeatherForCity(int cityId) {
        if (cachedWeathers == null || cachedWeathers.isEmpty()) {
            return null;
        }
        return cachedWeathers.get(cityId);
    }
}
