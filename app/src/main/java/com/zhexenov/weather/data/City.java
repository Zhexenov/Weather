package com.zhexenov.weather.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "cities")
public final class City {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private final int id;

    @NonNull
    @ColumnInfo(name = "country")
    private final String country;

    @NonNull
    @ColumnInfo(name = "name")
    private final String name;


    /** Cities resource: http://bulk.openweathermap.org/sample/city.list.json
     * @param id city id
     * @param name city name, additional desc
     * @param country country code
     */
    public City(int id, @NonNull String name, @NonNull String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        City city= (City) obj;
        return id == city.id;
    }
}
