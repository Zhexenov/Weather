package com.zhexenov.weather.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "weathers")
public class Weather {

    @PrimaryKey
    @ColumnInfo(name = "cityid")
    int id;


}
