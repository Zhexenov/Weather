package com.zhexenov.weather.data.source.cities;


import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.source.Local;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Singleton
public class CitiesRepository implements CitiesDataSource {

    private final CitiesDataSource citiesLocalDataSource;

    @Inject
    CitiesRepository(@Local CitiesDataSource citiesLocalDataSource) {
        this.citiesLocalDataSource = citiesLocalDataSource;
    }


    @Override
    public Maybe<City> getCity(int cityId) {
        return citiesLocalDataSource.getCity(cityId);
    }

    @Override
    public Single<List<City>> getCities(String searchText) {
        return citiesLocalDataSource.getCities(searchText);
    }
}
