package com.zhexenov.weather.api;

import com.zhexenov.weather.data.Weather;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("data/2.5/weather")
    Flowable<Weather> fetchWeather(@Query("id") int cityId,
                                   @Query("appid") String appKey,
                                   @Query("units") String unit);
}