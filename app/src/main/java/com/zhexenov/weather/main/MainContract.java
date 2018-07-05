package com.zhexenov.weather.main;

import com.zhexenov.weather.BasePresenter;
import com.zhexenov.weather.BaseView;

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void showCity();
    }

    interface Presenter extends BasePresenter<View> {
        void loadCity();
    }
}
