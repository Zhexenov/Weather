package com.zhexenov.weather.data.source.local;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.source.CitiesDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


@Singleton
public class CitiesLocalDataSource implements CitiesDataSource {

    private final CitiesDao citiesDao;

    @Inject
    CitiesLocalDataSource(CitiesDao citiesDao) {
        this.citiesDao = citiesDao;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCity(final int cityId, @NonNull final GetCityCallback callback) {

        citiesDao.getCityById(cityId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<City>() {
                    @Override
                    public void accept(City city) {
                        if (city == null) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onCityLoaded(city);
                        }
                    }
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void getCities(String searchText, @NonNull final LoadCitiesCallback callback) {
        citiesDao.loadCities(searchText).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<City>>() {
                    @Override
                    public void accept(List<City> cities) {
                        if (cities.isEmpty()) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onCitiesLoaded(cities);
                        }
                    }
                });
    }
}
