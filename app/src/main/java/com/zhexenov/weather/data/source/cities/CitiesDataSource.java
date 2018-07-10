package com.zhexenov.weather.data.source.cities;

import com.zhexenov.weather.data.City;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public interface CitiesDataSource {

    Maybe<City> getCity(int cityId);

    Single<List<City>> getCities(String searchText);

}
