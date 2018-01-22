package com.sergeydeveloper7.cityemergencyservice.di.components;

import android.content.Context;

import com.sergeydeveloper7.cityemergencyservice.di.modules.ApplicationModule;
import com.sergeydeveloper7.cityemergencyservice.view.activities.BaseActivity;
import com.sergeydeveloper7.cityemergencyservice.view.fragments.MainScreenFragment;
import com.sergeydeveloper7.cityemergencyservice.view.fragments.RegisterFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by serg on 02.01.18.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);
    void inject(MainScreenFragment mainScreenFragment);
    void inject(RegisterFragment registerFragment);
    Context context();
}
