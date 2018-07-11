package com.zhexenov.weather.main;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.source.cities.CitiesRepository;
import com.zhexenov.weather.data.source.weather.WeatherRepository;
import com.zhexenov.weather.data.source.weather.local.MySharedPreferences;
import com.zhexenov.weather.di.ActivityScoped;
import com.zhexenov.weather.util.WeatherUtils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

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
        String transl = WeatherUtils.transliterate(searchText);
        sharedPreferences.putData(MySharedPreferences.LAST_REQUEST, transl);
        disposable.clear();
        citiesRepository.getCities(transl)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<City>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(List<City> cities) {
                        if (view != null) {
                            if (cities.isEmpty()) {
                                view.showSearchError();
                                return;
                            }
                            view.showCities(cities);

                            for (City city : cities) {
                                disposable.add(weatherRepository.getSingleWeather(city.getId(), onlyValidCache)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(weather -> view.showWeatherForCity(weather, city),
                                                throwable -> view.showErrorForCity(city)));
                            }
                            if (!onlyValidCache) {
                                loadCities(searchText, true);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null) {
                            view.showCities(new ArrayList<>());
                            view.showSearchError();
                        }
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
