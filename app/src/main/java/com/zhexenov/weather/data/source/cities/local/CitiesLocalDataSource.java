package com.zhexenov.weather.data.source.cities.local;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.source.cities.CitiesDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;
import io.reactivex.Single;


@Singleton
public class CitiesLocalDataSource implements CitiesDataSource {

    private final CitiesDao citiesDao;

    @Inject
    CitiesLocalDataSource(CitiesDao citiesDao) {
        this.citiesDao = citiesDao;
    }


    @Override
    public Maybe<City> getCity(int cityId) {
        return citiesDao.getCityById(cityId);
    }

    @Override
    public Single<List<City>> getCities(String searchText) {
        return citiesDao.loadCities(searchText);


    }
}
