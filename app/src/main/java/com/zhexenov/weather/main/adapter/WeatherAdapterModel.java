package com.zhexenov.weather.main.adapter;

import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.Weather;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WeatherAdapterModel {

    @Nonnull
    private City city;
    @Nullable
    private Weather weather;

    public WeatherAdapterModel(@Nonnull City city, @Nullable Weather weather) {
        this.city = city;
        this.weather = weather;
    }

    @Nonnull
    public City getCity() {
        return city;
    }

    public void setCity(@Nonnull City city) {
        this.city = city;
    }

    @Nullable
    public Weather getWeather() {
        return weather;
    }

    public void setWeather(@Nullable Weather weather) {
        this.weather = weather;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WeatherAdapterModel model = (WeatherAdapterModel) obj;
        return city.equals(model.city);
    }

    @Override
    public int hashCode() {
        int id = 0;
        if (weather != null) {
            id = weather.getCityId();
        }
        return Objects.hash(city.getName(), id);
    }

    @Override
    public String toString() {
        if (weather == null) {
            return String.format("%s: Loading...", city.getName());
        }
        if (weather.getCityId() == -1) {
            return String.format("%s: Error", city.getName());
        }
        return String.format("%s: %s Celsius", city.getName(), (int) weather.getMain().getTemp());
    }
}
