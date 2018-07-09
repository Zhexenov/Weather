package com.zhexenov.weather.main;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.Weather;
import com.zhexenov.weather.data.source.cities.CitiesDataSource;
import com.zhexenov.weather.data.source.cities.CitiesRepository;
import com.zhexenov.weather.data.source.weather.WeatherDataSource;
import com.zhexenov.weather.data.source.weather.WeatherRepository;
import com.zhexenov.weather.data.source.weather.local.MySharedPreferences;
import com.zhexenov.weather.di.ActivityScoped;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;

@ActivityScoped
final class MainPresenter implements MainContract.Presenter {

    private final CitiesRepository citiesRepository;
    private final WeatherRepository weatherRepository;

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Nullable
    private MainContract.View view;

    @Inject
    MySharedPreferences sharedPreferences;

    @Inject
    MainPresenter(CitiesRepository citiesRepository, WeatherRepository weatherRepository) {
        this.citiesRepository = citiesRepository;
        this.weatherRepository = weatherRepository;
    }


    @Override
    public void loadCities(String searchText, boolean onlyValidCache) {
        citiesRepository.getCities(searchText, new CitiesDataSource.LoadCitiesCallback() {
            @Override
            public void onCitiesLoaded(List<City> cities) {
                if (view != null) {
                    view.showCities(cities);
                    loadWeathersForCities(cities, onlyValidCache, new WeatherDataSource.GetWeatherForCityCallback() {
                        @Override
                        public void onWeatherLoaded(Weather forecast, City city) {
                            view.showWeatherForCity(forecast, city);
                        }

                        @Override
                        public void onDataNotAvailable(City city) {
                            view.showErrorForCity(city);
                        }
                    });
                }
                sharedPreferences.putData(MySharedPreferences.LAST_REQUEST, searchText);
            }

            @Override
            public void onDataNotAvailable() {
                if (view != null) {
                    view.showCities(new ArrayList<>());
                    view.showSearchError();
                }
            }
        });
    }


    private void loadWeathersForCities(List<City> list, boolean onlyValidCache, @Nonnull WeatherDataSource.GetWeatherForCityCallback callback) {
        disposable.clear();
        disposable.add(Observable.fromIterable(list).
                flatMap((Function<City, ObservableSource<Weather>>) city -> {
                    weatherRepository.getWeatherForCity(city.getId(), onlyValidCache, new WeatherDataSource.GetWeatherCallback() {
                        @Override
                        public void onWeatherLoaded(Weather forecast) {
                            callback.onWeatherLoaded(forecast, city);
                        }

                        @Override
                        public void onDataNotAvailable() {
                            callback.onDataNotAvailable(city);
                        }
                    });
                    return Observable.just(new Weather(1, 1, 1f));
                })
                .doOnComplete(() -> {
                    if (!onlyValidCache) {
                        loadWeathersForCities(list, true, callback);
                    }
                })
                .subscribe());
    }

    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this presenter
     */
    @Override
    public void takeView(MainContract.View view) {
        this.view = view;
        String lastQuery = sharedPreferences.getData(MySharedPreferences.LAST_REQUEST);

        if (lastQuery != null) {
            if (view != null) {
                view.showLastQuery(lastQuery);
            }
            loadCities(lastQuery, false);
        }
    }

    /**
     * Drops the reference to the view when destroyed
     */
    @Override
    public void dropView() {
        view = null;
        disposable.clear();
    }
}
