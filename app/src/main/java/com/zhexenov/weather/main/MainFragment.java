package com.zhexenov.weather.main;

import android.app.Application;
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
import android.widget.EditText;

import com.zhexenov.weather.R;
import com.zhexenov.weather.data.City;
import com.zhexenov.weather.data.Weather;
import com.zhexenov.weather.main.adapter.CitiesAdapter;
import com.zhexenov.weather.main.adapter.WeatherAdapterModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import dagger.android.support.DaggerFragment;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainFragment extends DaggerFragment implements MainContract.View {

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    Application context;

    boolean ignoreChangesFlag = false;

    @Inject
    MainContract.Presenter presenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.city_name)
    EditText cityName;
    @BindView(R.id.initial_text)
    View initialTextView;
    @BindView(R.id.no_data_text)
    View noDataView;

    private CitiesAdapter adapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private List<WeatherAdapterModel> dataSet;

    @Inject
    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSet = new ArrayList<>();
        adapter = new CitiesAdapter(context, dataSet);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        presenter.dropView();
        disposable.clear();
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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showLastQuery(String text) {
        ignoreChangesFlag = true;
        cityName.setText(text);
        ignoreChangesFlag = false;
    }

    @Override
    public void showCities(List<City> cities) {
        disposable.clear();
        disposable.add(Completable.fromAction(() -> {
            dataSet = new ArrayList<>();
            for (City city : cities) {
                dataSet.add(new WeatherAdapterModel(city, null));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    if (!dataSet.isEmpty()) {
                        initialTextView.setVisibility(View.GONE);
                        noDataView.setVisibility(View.GONE);
                    }
                    adapter.updateDataSet(dataSet);
                })
                .subscribe());
    }

    @Override
    public void showSearchError() {
        noDataView.setVisibility(View.VISIBLE);
        initialTextView.setVisibility(View.GONE);
    }

    @Override
    public void showWeatherForCity(Weather weather, City city) {
        WeatherAdapterModel model = new WeatherAdapterModel(city, weather);
        int index = dataSet.indexOf(model);

        if (index != -1) {
            dataSet.set(index, model);
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
            if (ignoreChangesFlag) {
                return;
            }
            handler.removeCallbacks(runnable);
            runnable = () -> presenter.loadCities(text.toString(), true);
            handler.postDelayed(runnable, 300);
        }
    }
}
