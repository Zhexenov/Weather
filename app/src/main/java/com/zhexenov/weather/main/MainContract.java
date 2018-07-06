package com.zhexenov.weather.main;

import com.zhexenov.weather.BasePresenter;
import com.zhexenov.weather.BaseView;
import com.zhexenov.weather.data.City;

import java.util.List;

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void showCities(List<City> cities);
    }

    interface Presenter extends BasePresenter<View> {
        void loadCities(String searchText);
    }
}
