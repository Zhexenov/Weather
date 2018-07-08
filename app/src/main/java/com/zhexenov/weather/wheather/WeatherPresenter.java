package com.zhexenov.weather.wheather;

import javax.annotation.Nullable;

public class WeatherPresenter implements WeatherContract.Presenter {

    private WeatherContract.View view;
    private int cityId;

    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this presenter
     */
    @Override
    public void takeView(WeatherContract.View view) {
        this.view = view;
    }

    /**
     * Drops the reference to the view when destroyed
     */
    @Override
    public void dropView() {
        this.view = null;
    }
}
