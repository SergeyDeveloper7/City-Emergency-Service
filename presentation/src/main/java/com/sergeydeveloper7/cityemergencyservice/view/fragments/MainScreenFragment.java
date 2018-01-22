package com.sergeydeveloper7.cityemergencyservice.view.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.navigation.Navigator;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;
import com.sergeydeveloper7.cityemergencyservice.view.activities.MainActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainScreenFragment extends Fragment {

    private static final String TAG = MainScreenFragment.class.getSimpleName();
    private Context context;

    @Inject Navigator navigator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        ((MainActivity)getActivity()).getApplicationComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_screen, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.loginButton) void login(){

    }

    @OnClick(R.id.registerButton) void register(){
        navigator.startFragmentNoBackStack(context, new RegisterFragment(), Const.REGISTER_FRAGMENT_ID);
    }

}
