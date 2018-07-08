package com.zhexenov.weather.data.source.cities;

import android.support.annotation.NonNull;

import com.zhexenov.weather.data.City;

import java.util.List;

public interface CitiesDataSource {

    interface LoadCitiesCallback {

        void onCitiesLoaded(List<City> cities);

        void onDataNotAvailable();
    }

    interface GetCityCallback {

        void onCityLoaded(City city);

        void onDataNotAvailable();
    }

    void getCity(int cityId, @NonNull GetCityCallback callback);

    void getCities(String searchText, @NonNull LoadCitiesCallback callback);

}
