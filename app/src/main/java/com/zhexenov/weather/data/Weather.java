package com.zhexenov.weather.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;


@Entity(tableName = "weathers")
public final class Weather {

    @PrimaryKey
    @ColumnInfo(name = "city_id")
    private final int cityId;


    @ColumnInfo(name = "temp")
    private final int temp;


    @ColumnInfo(name = "wind_speed")
    private final int windSpeed;


    @ColumnInfo(name = "pressure")
    private final int pressure;


    @ColumnInfo(name = "humidity")
    private final int humidity;


    @ColumnInfo(name = "description")
    @NonNull
    private final String description;


    @ColumnInfo(name = "dt")
    private final int dateTime;

    /**
     * @param cityId unique city id
     * @param temp temperature in Celsius
     * @param windSpeed wind speed km/h per second
     * @param pressure atmospheric pressure
     * @param humidity humidity, %
     * @param description weather condition
     * @param dateTime timestamp
     */
    public Weather(int cityId, int temp, int windSpeed, int pressure, int humidity, @NonNull String description, int dateTime) {
        this.cityId = cityId;
        this.temp = temp;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.humidity = humidity;
        this.description = description;
        this.dateTime = dateTime;
    }


    public int getCityId() {
        return cityId;
    }

    public int getTemp() {
        return temp;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public int getDateTime() {
        return dateTime;
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
        return Objects.hash(cityId, dateTime, temp, windSpeed, pressure, humidity, description);
    }

}
