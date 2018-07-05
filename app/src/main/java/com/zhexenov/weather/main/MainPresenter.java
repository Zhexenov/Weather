package com.zhexenov.weather.main;

import android.util.Log;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.source.CitiesDataSource;
import com.zhexenov.weather.data.source.CitiesRepository;
import com.zhexenov.weather.di.ActivityScoped;

import javax.inject.Inject;

@ActivityScoped
final class MainPresenter implements MainContract.Presenter {

    private final CitiesRepository citiesRepository;


    @Inject
    MainPresenter(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }


    @Override
    public void loadCity() {
        Log.e("tag", "here");
        citiesRepository.getCity(1270260, new CitiesDataSource.GetCityCallback() {
            @Override
            public void onCityLoaded(City city) {
                Log.e("Tag:", city.getName());
            }
        });
    }

    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this presenter
     */
    @Override
    public void takeView(MainContract.View view) {

    }

    /**
     * Drops the reference to the view when destroyed
     */
    @Override
    public void dropView() {

    }
}
