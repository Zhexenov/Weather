package com.zhexenov.weather.data.source.local;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.zhexenov.weather.data.City;

import java.util.List;

@Dao
public interface CitiesDao {

    @Query("SELECT * FROM CITIES")
    List<City> getCities();

    @Query("SELECT * FROM CITIES WHERE id = :cityId")
    City getCityById(int cityId);
}
