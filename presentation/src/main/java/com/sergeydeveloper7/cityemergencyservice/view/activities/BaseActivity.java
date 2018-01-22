package com.sergeydeveloper7.cityemergencyservice.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sergeydeveloper7.cityemergencyservice.application.CityEmergencyServiceApplication;
import com.sergeydeveloper7.cityemergencyservice.di.components.ApplicationComponent;
import com.sergeydeveloper7.cityemergencyservice.navigation.Navigator;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {

    @Inject Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return ((CityEmergencyServiceApplication) getApplication()).getApplicationComponent();
    }
}
