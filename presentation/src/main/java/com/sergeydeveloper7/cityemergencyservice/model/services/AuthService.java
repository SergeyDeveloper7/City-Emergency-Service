package com.sergeydeveloper7.cityemergencyservice.model.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.sergeydeveloper7.cityemergencyservice.model.general.User;
import com.sergeydeveloper7.cityemergencyservice.model.request.SignInRequestModel;
import com.sergeydeveloper7.cityemergencyservice.model.request.SignUpRequestModel;
import com.sergeydeveloper7.cityemergencyservice.model.response.SignInResponseModel;
import com.sergeydeveloper7.cityemergencyservice.model.response.SignUpResponseModel;
import com.sergeydeveloper7.cityemergencyservice.network.RestClient;
import com.sergeydeveloper7.cityemergencyservice.network.RestInterface;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by serg on 06.02.18.
 */

public class AuthService {

    private Context                  context;
    private String                   tag;
    private RestInterface            client;
    private SharedPreferences        sharedPreferences;
    private User                     user;

    public AuthService(Context context, String tag) {
        this.context = context;
        this.tag = tag;
        client = new RestClient().getApiService();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        user = new Gson().fromJson(sharedPreferences.getString("user", null), User.class);
    }


    public Observable<Response<SignInResponseModel>> signIn(SignInRequestModel signInRequestModel){
        String request = new Gson().toJson(signInRequestModel);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), request);
        Log.d(tag, request);
        return client.signIn(body);
    }

    public Observable<Response<SignUpResponseModel>> signUp(SignUpRequestModel signInRequestModel){
        String request = new Gson().toJson(signInRequestModel);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), request);
        Log.d(tag, request);
        return client.signUp(body);
    }
}
