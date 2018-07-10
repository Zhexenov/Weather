package com.zhexenov.weather.data.source.weather.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.zhexenov.weather.data.Weather;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface WeathersDao {

    @Query("SELECT * FROM Weather WHERE city_id = :cityId")
    Single<Weather> getWeatherForCityId(int cityId);

    @Query("SELECT * FROM Weather WHERE city_id = :cityId")
    Weather weatherForCityId(int cityId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeather(Weather weather);

    @Delete
    void deleteWeather(Weather weather);
}
