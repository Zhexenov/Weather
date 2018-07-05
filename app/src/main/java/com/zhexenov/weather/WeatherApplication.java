package com.zhexenov.weather;

import com.zhexenov.weather.di.DaggerAppComponent;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class WeatherApplication extends DaggerApplication {

    /**
     * Implementations should return an {@link AndroidInjector} for the concrete {@link
     * DaggerApplication}. Typically, that injector is a {@link Component}.
     */
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

}
