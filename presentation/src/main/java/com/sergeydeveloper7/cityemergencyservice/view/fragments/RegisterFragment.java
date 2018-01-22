package com.sergeydeveloper7.cityemergencyservice.view.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.navigation.Navigator;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;
import com.sergeydeveloper7.cityemergencyservice.view.activities.MainActivity;
import com.sergeydeveloper7.cityemergencyservice.view.adapters.RegisterAdapter;
import com.sergeydeveloper7.data.models.Worker;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterFragment extends Fragment {

    private static final String TAG = RegisterFragment.class.getSimpleName();
    private Context             context;
    private RegisterAdapter     adapter;
    private Worker              worker;

    private ArrayList<String>   index = new ArrayList<>(Arrays.asList(
            Const.REGISTER_FIELD_LOGIN,
            Const.REGISTER_FIELD_PASSWORD,
            Const.REGISTER_FIELD_FIRST_NAME,
            Const.REGISTER_FIELD_LAST_NAME,
            Const.REGISTER_FIELD_PHONENUMBER,
            Const.REGISTER_FIELD_BUTTON));

    @BindView(R.id.registerFieldsRecyclerView) RecyclerView registerFieldsRecyclerView;

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
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, rootView);
        setLeadsRecyclerView();
        return rootView;
    }

    private void setLeadsRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        registerFieldsRecyclerView.setHasFixedSize(false);
        registerFieldsRecyclerView.setLayoutManager(layoutManager);
        registerFieldsRecyclerView.setItemViewCacheSize(50);
        registerFieldsRecyclerView.setDrawingCacheEnabled(true);
        RecyclerView.ItemAnimator animator = registerFieldsRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        adapter = new RegisterAdapter(context, index, this);
        registerFieldsRecyclerView.setAdapter(adapter);
    }

    public Worker getWorker() {
        return worker;
    }
}
