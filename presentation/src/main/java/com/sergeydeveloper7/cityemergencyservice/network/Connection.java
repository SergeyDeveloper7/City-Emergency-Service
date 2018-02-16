package com.sergeydeveloper7.cityemergencyservice.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sergeydeveloper7.cityemergencyservice.application.CityEmergencyServiceApplication;

/**
 * Created by serg on 25.01.18.
 */

public class Connection {

    public static boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) CityEmergencyServiceApplication.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }
}
