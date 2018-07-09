package com.zhexenov.weather.data;

import com.google.gson.annotations.SerializedName;

public class WeatherDto {

    @SerializedName("id")
    private int cityId;

    @SerializedName("dt")
    private int dateTime;

    @SerializedName("main")
    private Main main;


    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getDateTime() {
        return dateTime;
    }

    public void setDateTime(int dateTime) {
        this.dateTime = dateTime;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
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
