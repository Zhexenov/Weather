package com.zhexenov.weather.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferencesModule {

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(Application context) {
        return context.getSharedPreferences("WeatherSharedPreferences", Context.MODE_PRIVATE);
    }
}
