package com.sergeydeveloper7.cityemergencyservice.navigation;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;

import com.sergeydeveloper7.cityemergencyservice.R;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by serg on 02.01.18.
 */

@Singleton
public class Navigator {

    @Inject
    Navigator() {}

    public void startFragmentWithBackStack(Context context, Fragment fragment, String tag){
        final FragmentTransaction fragmentTransaction = ((Activity)context).getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null).replace(R.id.main_frame, fragment, tag).commit();
    }

    public void startFragmentNoBackStack(Context context, Fragment fragment, String tag){
        final FragmentTransaction fragmentTransaction = ((Activity)context).getFragmentManager().beginTransaction();
        for(int i = 0; i < ((Activity)context).getFragmentManager().getBackStackEntryCount(); ++i) {
            ((Activity)context).getFragmentManager().popBackStack();
        }
        fragmentTransaction.replace(R.id.main_frame, fragment, tag).commit();
    }

}
