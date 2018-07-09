package com.zhexenov.weather.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;


@Entity
public class Weather {

    @PrimaryKey
    @ColumnInfo(name = "city_id")
    private final int cityId;

    @ColumnInfo(name = "dt")
    private final int dateTime;

    @ColumnInfo(name = "temp")
    private final float temp;

    /**
     * @param cityId unique city id
     * @param dateTime timestamp
     * @param temp temperature in Celsius
     */
    public Weather(int cityId, int dateTime, float temp) {
        this.cityId = cityId;
        this.dateTime = dateTime;
        this.temp = temp;
    }


    public int getCityId() {
        return cityId;
    }

    public int getDateTime() {
        return dateTime;
    }

    public float getTemp() {
        return temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return cityId == weather.cityId && dateTime == weather.dateTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId, dateTime);
    }

}
