package com.zhexenov.weather.data;

import com.google.gson.annotations.SerializedName;

public class Forecast {

    @SerializedName("id")
    private int id;

    @SerializedName("dt")
    private int date;

    @SerializedName("weather")
    private WeatherDesc weather;

    @SerializedName("main")
    private Main main;

    @SerializedName("wind")
    private Wind wind;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public WeatherDesc getWeather() {
        return weather;
    }

    public void setWeather(WeatherDesc weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    static class Wind {
        @SerializedName("speed")
        private int speed;

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }
    }

    static class Main {
        @SerializedName("temp")
        private int temp;

        @SerializedName("pressure")
        private int pressure;

        @SerializedName("humidity")
        private int humidity;

        public int getTemp() {
            return temp;
        }

        public void setTemp(int temp) {
            this.temp = temp;
        }

        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }
    }

    static class WeatherDesc {
        @SerializedName("description")
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
