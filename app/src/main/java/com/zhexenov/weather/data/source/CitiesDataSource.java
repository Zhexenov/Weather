package com.zhexenov.weather.data.source;

import android.support.annotation.NonNull;

import com.zhexenov.weather.data.City;

import java.util.List;

public interface CitiesDataSource {

    interface LoadCitiesCallback {

        void onCitiesLoaded(List<City> cities);
    }

    interface GetCityCallback {

        void onCityLoaded(City city);
    }

    void getCity(int cityId, @NonNull GetCityCallback callback);
}
