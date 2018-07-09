package com.zhexenov.weather.main;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.Weather;
import com.zhexenov.weather.data.source.cities.CitiesDataSource;
import com.zhexenov.weather.data.source.cities.CitiesRepository;
import com.zhexenov.weather.data.source.weather.WeatherDataSource;
import com.zhexenov.weather.data.source.weather.WeatherRepository;
import com.zhexenov.weather.di.ActivityScoped;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@ActivityScoped
final class MainPresenter implements MainContract.Presenter {

    private final CitiesRepository citiesRepository;
    private final WeatherRepository weatherRepository;

    private Disposable disposable;

    @Nullable
    private MainContract.View view;

    @Inject
    MainPresenter(CitiesRepository citiesRepository, WeatherRepository weatherRepository) {
        this.citiesRepository = citiesRepository;
        this.weatherRepository = weatherRepository;
    }


    @Override
    public void loadCities(String searchText) {
        citiesRepository.getCities(searchText, new CitiesDataSource.LoadCitiesCallback() {
            @Override
            public void onCitiesLoaded(List<City> cities) {
                if (view != null) {
                    view.showCities(cities);
                    loadWeathersForCities(cities, new WeatherDataSource.GetWeatherForCityCallback() {
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
            }

            @Override
            public void onDataNotAvailable() {
                Timber.e("Data not available");
            }
        });
    }


    private void loadWeathersForCities(List<City> list, @Nonnull WeatherDataSource.GetWeatherForCityCallback callback) {
        if (disposable != null) {
            disposable.dispose();
        }
        disposable = Observable.fromIterable(list).
                flatMap((Function<City, ObservableSource<Weather>>) city -> {
                    weatherRepository.getWeatherForCity(city.getId(), new WeatherDataSource.GetWeatherCallback() {
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

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
