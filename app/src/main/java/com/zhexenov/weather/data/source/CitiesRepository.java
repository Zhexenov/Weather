package com.zhexenov.weather.data.source;


import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CitiesRepository implements CitiesDataSource {

    private final CitiesDataSource citiesLocalDataSource;

    @Inject
    CitiesRepository(CitiesDataSource citiesLocalDataSource) {
        this.citiesLocalDataSource = citiesLocalDataSource;
    }

    @Override
    public void getCity(int cityId, @NonNull GetCityCallback callback) {
        citiesLocalDataSource.getCity(cityId, callback);
    }
}
