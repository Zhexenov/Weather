package com.zhexenov.weather.main;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.Weather;
import com.zhexenov.weather.data.source.cities.CitiesDataSource;
import com.zhexenov.weather.data.source.cities.CitiesRepository;
import com.zhexenov.weather.data.source.weather.WeatherRepository;
import com.zhexenov.weather.di.ActivityScoped;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import timber.log.Timber;

@ActivityScoped
final class MainPresenter implements MainContract.Presenter {

    private final CitiesRepository citiesRepository;
    private final WeatherRepository weatherRepository;

    Handler handler = new Handler(Looper.getMainLooper());
    Runnable runnable;

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
                    loadWeathersForCities(cities);
                }
            }

            @Override
            public void onDataNotAvailable() {
                Timber.e("Data not available");
            }
        });
    }

    @SuppressLint("CheckResult")
    private void loadWeathersForCities(List<City> list) {
//        Observable.fromIterable(list).
//                flatMap((Function<City, ObservableSource<Weather>>) city -> Observable.just(weatherRepository.loadWeatherForCity(city.getId())))
//        .subscribe(new Consumer<Weather>() {
//            @Override
//            public void accept(Weather weather) throws Exception {
//
//            }
//        });


         Disposable disposable = Observable.fromIterable(list).
                flatMap((Function<City, ObservableSource<Weather>>) city -> Observable.just(new Weather(1, (int) System.currentTimeMillis(), 1.f)))
        .subscribe(new Consumer<Weather>() {
            @Override
            public void accept(Weather weather) throws Exception {
                Timber.e("Weather %s ", weather.getDateTime());
            }
        });

        handler.removeCallbacks(runnable);
        runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        handler.post(runnable);
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
