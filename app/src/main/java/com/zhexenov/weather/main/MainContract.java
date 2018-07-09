package com.zhexenov.weather.main;

import com.zhexenov.weather.BasePresenter;
import com.zhexenov.weather.BaseView;
import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.Weather;

import java.util.List;

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void showLastQuery(String text);

        void showCities(List<City> cities);

        void showSearchError();

        void showWeatherForCity(Weather weather, City city);

        void showErrorForCity(City city);
    }

    interface Presenter extends BasePresenter<View> {
        void loadCities(String searchText, boolean onlyValidCache);
    }
}
