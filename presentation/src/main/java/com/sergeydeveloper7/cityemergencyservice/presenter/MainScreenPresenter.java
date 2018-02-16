package com.sergeydeveloper7.cityemergencyservice.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.model.services.AuthService;
import com.sergeydeveloper7.cityemergencyservice.model.general.User;
import com.sergeydeveloper7.cityemergencyservice.model.request.SignInRequestModel;
import com.sergeydeveloper7.cityemergencyservice.model.response.Error;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;
import com.sergeydeveloper7.cityemergencyservice.view.basic.MainScreenView;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by serg on 06.02.18.
 */

public class MainScreenPresenter implements BasePresenter {

    private Context        context;
    private String         tag;
    private AuthService    authService;
    private MainScreenView view;
    private Subscription   signInSubscription;

    public MainScreenPresenter(Context context, String tag, MainScreenView view) {
        this.context = context;
        this.tag = tag;
        this.view = view;
        authService = new AuthService(context, tag);
    }

    public void signIn(SignInRequestModel signInRequestModel){
        view.beforeRequest();
        signInSubscription = authService.signIn(signInRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    System.out.println(response.message() + "\n" + response.raw() + "\n" + response.headers() + "\n");
                    Log.d(tag, " result " + new Gson().toJson(response.body()));
                    Gson gson = new GsonBuilder().create();
                    Error error;
                    try {
                        if(response.errorBody() != null){
                            error = gson.fromJson(response.errorBody().string(), Error.class);
                            if(error != null && error.getErrors() != null && error.getErrors().size() > 0) {
                                Log.d(tag, " Error ");
                                Log.d(tag, " status: " + error.getErrors().get(0).getStatus());
                                Log.d(tag, " title: " + error.getErrors().get(0).getTitle());
                                Log.d(tag, " detail: " + error.getErrors().get(0).getDetail());
                                view.requestRuntimeError(error.getErrors().get(0).getDetail());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(response.isSuccessful() && response.body() != null){
                        Log.d(tag, "result is OK " + new Gson().toJson(response.body()));
                        if(response.body().isSuccess()){
                            User user = response.body().getBody().getUser();
                            user.setAuthToken(response.body().getBody().getToken());
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user", new Gson().toJson(user));
                            editor.putBoolean(Const.SHARED_PREFERENCE_IS_USER_LOGIN, true);
                            editor.apply();
                            view.afterRequest(response.body().isSuccess(), response.body().getBody().getUser().getRole());
                        } else
                            view.afterRequest(response.body().isSuccess(), "");
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    if(throwable instanceof ConnectException || throwable instanceof SocketTimeoutException)
                        view.requestRuntimeError(context.getResources().getString(R.string.global_internet_connection_error));
                });
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        if(signInSubscription != null && !signInSubscription.isUnsubscribed())
            signInSubscription.unsubscribe();
    }
}
