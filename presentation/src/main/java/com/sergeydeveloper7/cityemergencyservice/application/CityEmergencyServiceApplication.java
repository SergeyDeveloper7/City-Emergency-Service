package com.sergeydeveloper7.cityemergencyservice.application;

import android.support.multidex.MultiDexApplication;

import com.sergeydeveloper7.cityemergencyservice.di.components.ApplicationComponent;
import com.sergeydeveloper7.cityemergencyservice.di.components.DaggerApplicationComponent;
import com.sergeydeveloper7.cityemergencyservice.di.modules.ApplicationModule;

/**
 * Created by serg on 22.01.18.
 */

public class CityEmergencyServiceApplication extends MultiDexApplication {

    private static CityEmergencyServiceApplication mInstance;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        this.initializeInjector();
    }

    public static synchronized CityEmergencyServiceApplication getInstance() {
        return mInstance;
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
