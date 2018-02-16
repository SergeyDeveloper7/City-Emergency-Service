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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.model.request.SignUpRequestModel;
import com.sergeydeveloper7.cityemergencyservice.navigation.Navigator;
import com.sergeydeveloper7.cityemergencyservice.presenter.RegisterScreenPresenter;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;
import com.sergeydeveloper7.cityemergencyservice.view.activities.MainActivity;
import com.sergeydeveloper7.cityemergencyservice.view.adapters.RegisterAdapter;
import com.sergeydeveloper7.cityemergencyservice.view.basic.RegisterScreenView;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterFragment extends Fragment implements RegisterScreenView{

    private static final String     TAG = RegisterFragment.class.getSimpleName();
    private Context                 context;
    private RegisterAdapter         adapter;
    private RegisterScreenPresenter presenter;

    private ArrayList<String>   index = new ArrayList<>(Arrays.asList(
            Const.REGISTER_FIELD_LOGIN,
            Const.REGISTER_FIELD_PASSWORD,
            Const.REGISTER_FIELD_FIRST_NAME,
            Const.REGISTER_FIELD_LAST_NAME,
            Const.REGISTER_FIELD_PHONENUMBER,
            Const.REGISTER_FIELD_BUTTON));

    @BindView(R.id.registerFieldsRecyclerView) RecyclerView registerFieldsRecyclerView;
    @BindView(R.id.registerProgressBar)        ProgressBar registerProgressBar;

    @Inject Navigator navigator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        ((MainActivity)getActivity()).getApplicationComponent().inject(this);
        presenter = new RegisterScreenPresenter(context, TAG, this);
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

    public void register(SignUpRequestModel signUpRequestModel){
        presenter.signUp(signUpRequestModel);
    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void requestRuntimeError(String error) {

    }

    @Override
    public void afterRequest(boolean successful) {
        if(successful){
            this.navigator.startFragmentNoBackStack(context, new MainScreenFragment(), Const.MAIN_SCREEN_FRAGMENT_ID);
            Toast.makeText(context, context.getString(R.string.register_screen_successful), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(context, context.getString(R.string.register_screen_no_successful), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connectionErrorResponse() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
