package com.zhexenov.weather.modules;


import android.app.Application;
import android.arch.persistence.room.Room;

import com.huma.room_for_asset.RoomAsset;
import com.zhexenov.weather.data.source.CitiesDataSource;
import com.zhexenov.weather.data.source.local.CitiesDao;
import com.zhexenov.weather.data.source.local.CitiesLocalDataSource;
import com.zhexenov.weather.data.source.local.WeatherDatabase;
import com.zhexenov.weather.util.AppExecutors;
import com.zhexenov.weather.util.DiskIOThreadExecutor;

import java.util.concurrent.Executors;

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
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }

    @Singleton
    @Binds
    abstract CitiesDataSource provideCitiesDataSource(CitiesLocalDataSource dataSource);
}
