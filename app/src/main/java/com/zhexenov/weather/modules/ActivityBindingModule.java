package com.zhexenov.weather.modules;

import com.zhexenov.weather.di.ActivityScoped;
import com.zhexenov.weather.main.MainActivity;
import com.zhexenov.weather.main.MainModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

}
