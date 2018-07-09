package com.zhexenov.weather.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhexenov.weather.R;
import com.zhexenov.weather.WeatherApplication;
import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.Weather;
import com.zhexenov.weather.main.adapter.CitiesAdapter;
import com.zhexenov.weather.main.adapter.WeatherAdapterModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import dagger.android.support.DaggerFragment;
import timber.log.Timber;

public class MainFragment extends DaggerFragment implements MainContract.View {

    @Inject
    MainContract.Presenter presenter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    CitiesAdapter adapter;

    Handler handler = new Handler(Looper.getMainLooper());
    Runnable runnable;
    private List<WeatherAdapterModel> dataSet;

    @Inject
    public MainFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSet = new ArrayList<>();
        adapter = new CitiesAdapter(dataSet);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        presenter.dropView();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);
        configureRecyclerView();
        return root;
    }


    private void configureRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(WeatherApplication.getAppContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(WeatherApplication.getAppContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showCities(List<City> cities) {
        dataSet = new ArrayList<>();
        for (City city : cities) {
            dataSet.add(new WeatherAdapterModel(city, null));
        }
        adapter.updateDataSet(dataSet);
    }

    @Override
    public void showWeatherForCity(Weather weather, City city) {
        WeatherAdapterModel model = new WeatherAdapterModel(city, weather);
        int index = dataSet.indexOf(model);
        if (index != -1) {
            dataSet.get(index).setWeather(weather);
            adapter.updateDataSet(dataSet);
        }
    }

    @Override
    public void showErrorForCity(City city) {
        // new Weather(-1, -1, -1.f) for error weather
        WeatherAdapterModel model = new WeatherAdapterModel(city, new Weather(-1, -1, -1.f));
        int index = dataSet.indexOf(model);
        if (index != -1) {
            dataSet.set(index, model);
            adapter.updateDataSet(dataSet);
        }
    }

    @OnTextChanged(R.id.city_name)
    void onTextChanged(final CharSequence text) {
        if (text.length() > 1) {
            handler.removeCallbacks(runnable);
            runnable = () -> presenter.loadCities(text.toString());
            handler.postDelayed(runnable, 300);
        }
    }
}
