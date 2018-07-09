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
import com.zhexenov.weather.util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

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

    @Inject
    public MainFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CitiesAdapter(new ArrayList<City>());
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
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(WeatherApplication.getAppContext(),
                recyclerView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        City city = adapter.getItemAt(position);
                        Timber.e("Item click: %d", city.getId());
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
    }

    @Override
    public void showCities(List<City> cities) {
        adapter.updateDataSet(cities);
    }

    @Override
    public void showWeatherForCity(Weather weather, City city) {

    }

    @Override
    public void showErrorForCity(City city) {

    }

    @OnTextChanged(R.id.city_name)
    void onTextChanged(final CharSequence text) {

        if (text.length() > 1) {
            handler.removeCallbacks(runnable);
            runnable = () -> searchCities(text.toString());
            handler.postDelayed(runnable, 300);
        }
    }

    private void  searchCities(String text) {
        presenter.loadCities(text);
    }
}
