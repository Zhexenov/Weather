package com.zhexenov.weather.data.source.weather;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zhexenov.weather.data.Weather;
import com.zhexenov.weather.data.source.Local;
import com.zhexenov.weather.data.source.Remote;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;
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
    public Weather loadWeatherForCity(int cityId) {
        return null;
    }

    @Override
    public void getWeatherForCity(int cityId, @NonNull GetWeatherCallback callback) {
        Weather weather = getWeatherForCity(cityId);
        if (weather != null) {
            if (isValidCache(weather)) {
                callback.onWeatherLoaded(weather);
                return;
            }
            cachedWeathers.remove(cityId);
        }

        localDataSource.getWeatherForCity(cityId, new GetWeatherCallback() {
            @Override
            public void onWeatherLoaded(Weather forecast) {
                if (isValidCache(forecast)) {
                    cacheWeather(forecast);
                    callback.onWeatherLoaded(forecast);
                } else {
                    loadRemoteWeatherForCity(cityId, callback);
                }
            }

            @Override
            public void onDataNotAvailable() {
                loadRemoteWeatherForCity(cityId, callback);
            }
        });
    }

    @Override
    public void saveWeather(@NonNull Weather forecast) {
        localDataSource.saveWeather(forecast);
    }

    @Override
    public void deleteWeather(@NonNull Weather forecast) {
        localDataSource.deleteWeather(forecast);
    }

    private void loadRemoteWeatherForCity(int cityId, @NonNull GetWeatherCallback callback) {
        remoteDataSource.getWeatherForCity(cityId, new GetWeatherCallback() {
            @Override
            public void onWeatherLoaded(Weather forecast) {
                Timber.e("Loaded forecast: %s - %s", forecast.getCityId(), forecast.getTemp());
                cacheWeather(forecast);
                saveWeather(forecast);
                callback.onWeatherLoaded(forecast);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void cacheWeather(@NonNull Weather forecast) {
        if (cachedWeathers == null) {
            cachedWeathers = new LinkedHashMap<>();
        }
        cachedWeathers.put(forecast.getCityId(), forecast);
    }

    private boolean isValidCache(@NonNull Weather weather) {
        long seconds = System.currentTimeMillis() / 1000;
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
