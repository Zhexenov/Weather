package com.zhexenov.weather.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.huma.room_for_asset.RoomAsset;
import com.zhexenov.weather.data.City;

@Database(entities = {City.class}, version = 2)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract CitiesDao citiesDao();
}
