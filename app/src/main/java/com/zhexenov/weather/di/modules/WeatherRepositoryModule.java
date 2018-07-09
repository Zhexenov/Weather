package com.zhexenov.weather.di.modules;


import android.app.Application;
import android.arch.persistence.room.Room;

import com.huma.room_for_asset.RoomAsset;
import com.zhexenov.weather.data.source.CitiesDatabase;
import com.zhexenov.weather.data.source.Remote;
import com.zhexenov.weather.data.source.cities.CitiesDataSource;
import com.zhexenov.weather.data.source.Local;
import com.zhexenov.weather.data.source.cities.local.CitiesDao;
import com.zhexenov.weather.data.source.cities.local.CitiesLocalDataSource;
import com.zhexenov.weather.data.source.WeatherDatabase;
import com.zhexenov.weather.data.source.weather.WeatherDataSource;
import com.zhexenov.weather.data.source.weather.local.WeatherLocalDataSource;
import com.zhexenov.weather.data.source.weather.local.WeathersDao;
import com.zhexenov.weather.data.source.weather.remote.WeatherRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


@Module
abstract public class WeatherRepositoryModule {

    @Singleton
    @Provides
    static WeatherDatabase provideWeathersDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class, "Weather.db")
                .allowMainThreadQueries()
                .build();
    }

    @Singleton
    @Provides
    static CitiesDatabase provideCitiesDb(Application context) {
        return RoomAsset.databaseBuilder(context.getApplicationContext(), CitiesDatabase.class, "CitiesDatabase.db")
                .build();
    }

    @Singleton
    @Provides
    static CitiesDao provideCitiesDao(CitiesDatabase db) {
        return db.citiesDao();
    }

    @Singleton
    @Provides
    static WeathersDao provideWeathersDao(WeatherDatabase db) {
        return db.weathersDao();
    }


    @Singleton
    @Binds
    @Local
    abstract CitiesDataSource provideCitiesDataSource(CitiesLocalDataSource dataSource);


    @Singleton
    @Binds
    @Local
    abstract WeatherDataSource provideWeathersLocalDataSource(WeatherLocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract WeatherDataSource provideWeathersRemoteDataSource(WeatherRemoteDataSource dataSource);

}
