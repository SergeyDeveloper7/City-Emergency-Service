package com.sergeydeveloper7.cityemergencyservice.di.modules;

import android.content.Context;

import com.sergeydeveloper7.cityemergencyservice.application.CityEmergencyServiceApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by serg on 02.01.18.
 */

@Module
public class ApplicationModule {

    private final CityEmergencyServiceApplication application;

    public ApplicationModule(CityEmergencyServiceApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }
}
