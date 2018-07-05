package com.zhexenov.weather.data.source.local;

import android.support.annotation.NonNull;
import android.util.Log;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.source.CitiesDataSource;
import com.zhexenov.weather.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class CitiesLocalDataSource implements CitiesDataSource {

    private final CitiesDao citiesDao;

    private final AppExecutors appExecutors;

    @Inject
    CitiesLocalDataSource(CitiesDao citiesDao, AppExecutors appExecutors) {
        this.citiesDao = citiesDao;
        this.appExecutors = appExecutors;
    }

    @Override
    public void getCity(final int cityId, @NonNull final GetCityCallback callback) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final City city = citiesDao.getCityById(cityId);
                List<City> list = citiesDao.getCities();
                Log.e("tag", "size: " + list.size());
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (city != null) {
                            callback.onCityLoaded(city);
                        } else {
                            Log.e("tag", "city is null");
                        }
                    }
                });
            }
        };

        appExecutors.diskIO().execute(runnable);
    }


}
