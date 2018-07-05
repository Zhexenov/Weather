package com.zhexenov.weather.di;

import android.app.Application;

import com.zhexenov.weather.WeatherApplication;
import com.zhexenov.weather.data.source.CitiesRepository;
import com.zhexenov.weather.modules.ActivityBindingModule;
import com.zhexenov.weather.modules.ApplicationModule;
import com.zhexenov.weather.modules.WeatherRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {WeatherRepositoryModule.class,
        ApplicationModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBindingModule.class})
public interface AppComponent extends AndroidInjector<WeatherApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
