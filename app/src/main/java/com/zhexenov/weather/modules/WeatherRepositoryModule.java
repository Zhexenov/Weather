package com.zhexenov.weather.modules;


import android.app.Application;

import com.huma.room_for_asset.RoomAsset;
import com.zhexenov.weather.data.source.CitiesDataSource;
import com.zhexenov.weather.data.source.Local;
import com.zhexenov.weather.data.source.local.CitiesDao;
import com.zhexenov.weather.data.source.local.CitiesLocalDataSource;
import com.zhexenov.weather.data.source.local.WeatherDatabase;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


@Module
abstract public class WeatherRepositoryModule {

    private static final int THREAD_COUNT = 3;

    @Singleton
    @Provides
    static WeatherDatabase provideDb(Application context) {
        return RoomAsset.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class, "Weather.db")
                .fallbackToDestructiveMigration()
                .fallbackToDestructiveMigrationFrom()
                .build();
    }

    @Singleton
    @Provides
    static CitiesDao provideCitiesDao(WeatherDatabase db) {
        return db.citiesDao();
    }


    @Singleton
    @Binds
    @Local
    abstract CitiesDataSource provideCitiesDataSource(CitiesLocalDataSource dataSource);
}
