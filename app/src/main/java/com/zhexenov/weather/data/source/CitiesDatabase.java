package com.zhexenov.weather.data.source;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.source.cities.local.CitiesDao;

@Database(entities = { City.class }, version = 2)
public abstract class CitiesDatabase extends RoomDatabase {

    public abstract CitiesDao citiesDao();
}
