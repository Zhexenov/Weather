package com.zhexenov.weather.main;

import com.zhexenov.weather.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainModule {

    @ActivityScoped
    @Binds
    abstract MainContract.Presenter mainPresenter(MainPresenter presenter);
}
