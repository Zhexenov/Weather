package com.zhexenov.weather.wheather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhexenov.weather.data.Weather;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class WeatherFragment extends DaggerFragment implements WeatherContract.View {


    @Inject
    public WeatherFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showTitle(String title) {

    }

    @Override
    public void showWeather(Weather weather) {

    }

    @Override
    public void showError(String error) {

    }
}
