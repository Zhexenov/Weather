package com.zhexenov.weather.wheather;

import com.zhexenov.weather.BasePresenter;
import com.zhexenov.weather.BaseView;
import com.zhexenov.weather.data.Weather;

public interface WeatherContract {

    interface View  extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showTitle(String title);

        void showWeather(Weather weather);

        void showError(String error);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
