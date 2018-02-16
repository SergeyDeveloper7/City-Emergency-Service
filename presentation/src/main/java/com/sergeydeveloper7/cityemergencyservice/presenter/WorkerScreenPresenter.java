package com.sergeydeveloper7.cityemergencyservice.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.model.response.Error;
import com.sergeydeveloper7.cityemergencyservice.model.services.BidsService;
import com.sergeydeveloper7.cityemergencyservice.view.basic.WorkerScreenView;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by serg on 06.02.18.
 */

public class WorkerScreenPresenter implements BasePresenter {

    private Context                  context;
    private String                   tag;
    private BidsService              bidsService;
    private WorkerScreenView         view;
    private Subscription             getActiveBidsSubscription;

    public WorkerScreenPresenter(Context context, String tag, WorkerScreenView view) {
        this.context = context;
        this.tag = tag;
        this.view = view;
        bidsService = new BidsService(context, tag);
    }

    public void getBids(String status){
        view.beforeRequest();
        getActiveBidsSubscription = bidsService.getBids(status)
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
                        view.getAllBidsRequest(response.body().getBids());
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
        if(getActiveBidsSubscription != null && !getActiveBidsSubscription.isUnsubscribed())
            getActiveBidsSubscription.unsubscribe();
    }
}
