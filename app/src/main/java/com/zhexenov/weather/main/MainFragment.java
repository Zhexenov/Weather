package com.zhexenov.weather.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhexenov.weather.R;
import com.zhexenov.weather.WeatherApplication;
import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.Weather;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import dagger.android.support.DaggerFragment;

public class MainFragment extends DaggerFragment implements MainContract.View {

    @Inject
    MainContract.Presenter presenter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    CitiesAdapter adapter;

    @Inject
    public MainFragment() {
    }

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
        super.onDestroy();
        presenter.dropView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(WeatherApplication.getAppContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        return root;
    }


    @Override
    public void showCities(List<City> cities) {
        adapter.updateDataSet(cities);
    }

    @OnTextChanged(R.id.city_name)
    void onTextChanged(CharSequence text) {
        if (text.length() > 1) {
            presenter.loadCities(String.valueOf(text));
        }
    }
}
