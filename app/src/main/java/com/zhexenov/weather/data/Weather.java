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
    @SerializedName("id")
    private final int cityId;

    @ColumnInfo(name = "dt")
    @SerializedName("dt")
    private final int dateTime;

    @ColumnInfo(name = "temp")
    @SerializedName("main")
    private final Main main;

    /**
     * @param cityId unique city id
     * @param dateTime timestamp
     */
    public Weather(int cityId, int dateTime, Main main) {
        this.cityId = cityId;
        this.dateTime = dateTime;
        this.main = main;
    }


    public int getCityId() {
        return cityId;
    }

    public int getDateTime() {
        return dateTime;
    }

    public Main getMain() {
        return main;
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



    public static class Main {

        @SerializedName("temp")
        private float temp;

        public float getTemp() {
            return temp;
        }

        public void setTemp(int temp) {
            this.temp = temp;
        }
    }
}
