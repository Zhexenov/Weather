package com.zhexenov.weather.data.source.local;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.zhexenov.weather.data.City;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface CitiesDao {

    @Query("SELECT * FROM CITIES WHERE name LIKE :text || '%'")
    Flowable<List<City>> loadCities(String text);

    @Query("SELECT * FROM CITIES WHERE id = :cityId")
    Maybe<City> getCityById(int cityId);
}
