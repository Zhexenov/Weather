package com.zhexenov.weather.main;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.zhexenov.weather.R;
import com.zhexenov.weather.util.ActivityUtils;

import java.util.Objects;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

import static com.zhexenov.weather.R.string.cities;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    MainPresenter mainPresenter;

    @Inject
    Lazy<MainFragment> mainFragmentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(cities);
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (mainFragment == null) {
            mainFragment = mainFragmentProvider.get();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mainFragment, R.id.contentFrame);
        }


    }
}
