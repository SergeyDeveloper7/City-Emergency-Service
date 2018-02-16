package com.sergeydeveloper7.cityemergencyservice.view.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.model.request.SignInRequestModel;
import com.sergeydeveloper7.cityemergencyservice.navigation.Navigator;
import com.sergeydeveloper7.cityemergencyservice.network.Connection;
import com.sergeydeveloper7.cityemergencyservice.presenter.MainScreenPresenter;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;
import com.sergeydeveloper7.cityemergencyservice.utils.GenericMethods;
import com.sergeydeveloper7.cityemergencyservice.view.activities.MainActivity;
import com.sergeydeveloper7.cityemergencyservice.view.basic.MainScreenView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainScreenFragment extends Fragment implements MainScreenView {

    private static final String TAG = MainScreenFragment.class.getSimpleName();
    private Context             context;
    private MainScreenPresenter presenter;
    private MainActivity        mainActivity;

    @Inject Navigator navigator;

    @BindView(R.id.emailAddressEditText)    EditText emailAddressEditText;
    @BindView(R.id.passwordEditText)        EditText passwordEditText;
    @BindView(R.id.mainScreenContentLayout) RelativeLayout mainScreenContentLayout;
    @BindView(R.id.loginProgressBar)        ProgressBar loginProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        ((MainActivity)getActivity()).getApplicationComponent().inject(this);
        presenter = new MainScreenPresenter(context, TAG, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_screen, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainActivity = (MainActivity) getActivity();
        if (mainActivity != null && mainActivity.getToolbar() != null) {
            mainActivity.getToolbar().setVisibility(View.GONE);
            mainActivity.getAppBarLayout().setVisibility(View.GONE);
            mainActivity.getToolbar().setTitle("");
        }
    }

    @OnClick(R.id.loginButton) void login(){

        GenericMethods.hideKeyboard((Activity) context);
        String username = emailAddressEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(username.isEmpty()){
            Toast.makeText(context, context.getString(R.string.register_screen_login_hint), Toast.LENGTH_SHORT).show();
        } else if(password.isEmpty()){
            Toast.makeText(context, context.getString(R.string.register_screen_password_hint), Toast.LENGTH_SHORT).show();
        } else {
            if(Connection.isConnected()){
                SignInRequestModel signInRequestModel = new SignInRequestModel();
                signInRequestModel.setUsername(username);
                signInRequestModel.setPassword(password);
                presenter.signIn(signInRequestModel);
            } else {
                Toast.makeText(context, context.getString(R.string.internet_connection_is_not_available), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.registerButton) void register(){
        navigator.startFragmentNoBackStack(context, new RegisterFragment(), Const.REGISTER_FRAGMENT_ID);
    }

    @Override
    public void beforeRequest() {
        mainScreenContentLayout.setVisibility(View.GONE);
        loginProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestRuntimeError(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        mainScreenContentLayout.setVisibility(View.VISIBLE);
        loginProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void afterRequest(boolean successful, String role) {
        mainScreenContentLayout.setVisibility(View.VISIBLE);
        loginProgressBar.setVisibility(View.GONE);
        if(successful){
            if(role.equals(context.getString(R.string.role_worker)))
                navigator.startFragmentNoBackStack(context, new WorkersScreenFragment(), Const.WORKER_FRAGMENT_ID);
            else
                navigator.startFragmentNoBackStack(context, new AdminFragment(), Const.ADMIN_FRAGMENT_ID);
        } else {
            Toast.makeText(context, context.getString(R.string.main_screen_sign_in_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void connectionErrorResponse() {
        mainScreenContentLayout.setVisibility(View.VISIBLE);
        loginProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
