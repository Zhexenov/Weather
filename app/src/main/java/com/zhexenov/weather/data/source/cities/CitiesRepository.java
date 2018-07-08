package com.zhexenov.weather.data.source.cities;


import android.support.annotation.NonNull;

import com.zhexenov.weather.data.source.Local;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CitiesRepository implements CitiesDataSource {

    private final CitiesDataSource citiesLocalDataSource;

    @Inject
    CitiesRepository(@Local CitiesDataSource citiesLocalDataSource) {
        this.citiesLocalDataSource = citiesLocalDataSource;
    }

    @Override
    public void getCity(int cityId, @NonNull GetCityCallback callback) {
        citiesLocalDataSource.getCity(cityId, callback);
    }

    @Override
    public void getCities(String searchText, @NonNull LoadCitiesCallback callback) {
        citiesLocalDataSource.getCities(searchText, callback);
    }
}
