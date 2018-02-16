package com.sergeydeveloper7.cityemergencyservice.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.model.services.AuthService;
import com.sergeydeveloper7.cityemergencyservice.model.request.SignUpRequestModel;
import com.sergeydeveloper7.cityemergencyservice.model.response.Error;
import com.sergeydeveloper7.cityemergencyservice.view.basic.RegisterScreenView;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by serg on 06.02.18.
 */

public class RegisterScreenPresenter implements BasePresenter {

    private Context                  context;
    private String                   tag;
    private AuthService              authService;
    private RegisterScreenView       view;
    private Subscription             signUpSubscription;

    public RegisterScreenPresenter(Context context, String tag, RegisterScreenView view) {
        this.context = context;
        this.tag = tag;
        this.view = view;
        authService = new AuthService(context, tag);
    }

    public void signUp(SignUpRequestModel signUpRequestModel){
        view.beforeRequest();
        signUpSubscription = authService.signUp(signUpRequestModel)
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
                        view.afterRequest(response.body().isSuccess());
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
        if(signUpSubscription != null && !signUpSubscription.isUnsubscribed())
            signUpSubscription.unsubscribe();
    }
}
