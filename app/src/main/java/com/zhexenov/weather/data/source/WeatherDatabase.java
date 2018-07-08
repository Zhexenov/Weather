package com.zhexenov.weather.data.source;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.Converters;
import com.zhexenov.weather.data.Weather;
import com.zhexenov.weather.data.source.cities.local.CitiesDao;
import com.zhexenov.weather.data.source.weather.local.WeathersDao;

@Database(entities = { Weather.class }, version = 2)
@TypeConverters({ Converters.class })
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract WeathersDao weathersDao();
}
