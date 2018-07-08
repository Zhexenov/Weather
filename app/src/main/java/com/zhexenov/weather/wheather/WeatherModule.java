package com.zhexenov.weather.wheather;

import com.zhexenov.weather.di.ActivityScoped;
import com.zhexenov.weather.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class WeatherModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract WeatherFragment weatherFragment();

    @ActivityScoped
    @Binds
    abstract WeatherContract.Presenter weatherPresenter(WeatherPresenter presenter);

    @Provides
    @ActivityScoped
    static int provideCityId(WeatherActivity activity) {
        return activity.getIntent().getIntExtra(WeatherActivity.EXTRA_CITY_ID, 0);
    }

}
