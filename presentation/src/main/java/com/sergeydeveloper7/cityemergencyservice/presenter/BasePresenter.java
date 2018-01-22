package com.sergeydeveloper7.cityemergencyservice.presenter;

/**
 * Created by serg on 02.01.18.
 */

public interface BasePresenter {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();
}
