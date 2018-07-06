package com.zhexenov.weather;

import android.content.Context;

import com.zhexenov.weather.di.DaggerAppComponent;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

public class WeatherApplication extends DaggerApplication {

    private static Context context;
    /**
     * Implementations should return an {@link AndroidInjector} for the concrete {@link
     * DaggerApplication}. Typically, that injector is a {@link Component}.
     */
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        WeatherApplication.context = getApplicationContext();
        Timber.plant(new Timber.DebugTree());
    }

    public static Context getAppContext() {
        return WeatherApplication.context;
    }
}
