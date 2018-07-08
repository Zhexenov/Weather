package com.zhexenov.weather.main;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.source.CitiesDataSource;
import com.zhexenov.weather.data.source.CitiesRepository;
import com.zhexenov.weather.di.ActivityScoped;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import timber.log.Timber;

@ActivityScoped
final class MainPresenter implements MainContract.Presenter {

    private final CitiesRepository citiesRepository;

    @Nullable
    private MainContract.View view;

    @Inject
    MainPresenter(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }


    @Override
    public void loadCities(String searchText) {
        citiesRepository.getCities(searchText, new CitiesDataSource.LoadCitiesCallback() {
            @Override
            public void onCitiesLoaded(List<City> cities) {
                if (view != null) {
                    view.showCities(cities);
                }
            }

            @Override
            public void onDataNotAvailable() {
                Timber.e("Data not available");
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
        this.view = view;
    }

    /**
     * Drops the reference to the view when destroyed
     */
    @Override
    public void dropView() {
        view = null;
    }
}
