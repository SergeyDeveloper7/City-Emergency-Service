package com.sergeydeveloper7.cityemergencyservice.model.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.sergeydeveloper7.cityemergencyservice.model.general.User;
import com.sergeydeveloper7.cityemergencyservice.model.request.EditBidRequestModel;
import com.sergeydeveloper7.cityemergencyservice.model.request.NewBidRequestModel;
import com.sergeydeveloper7.cityemergencyservice.model.response.GetBidsResponse;
import com.sergeydeveloper7.cityemergencyservice.model.response.NewBidResponseModel;
import com.sergeydeveloper7.cityemergencyservice.network.RestClient;
import com.sergeydeveloper7.cityemergencyservice.network.RestInterface;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by serg on 06.02.18.
 */

public class BidsService {

    private Context                  context;
    private String                   tag;
    private RestInterface            client;
    private SharedPreferences        sharedPreferences;
    private User                     user;

    public BidsService(Context context, String tag) {
        this.context = context;
        this.tag = tag;
        client = new RestClient().getApiService();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        user = new Gson().fromJson(sharedPreferences.getString("user", null), User.class);
    }

    public Observable<Response<GetBidsResponse>> getBids(String status){
        Map<String, String> headers = new HashMap<>();
        headers.put("auth-token", user.getAuthToken());
        return client.getBids(headers, status);
    }

    public Observable<Response<NewBidResponseModel>> newBid(NewBidRequestModel newBidRequestModel){
        Map<String, String> headers = new HashMap<>();
        headers.put("auth-token", user.getAuthToken());
        String request = new Gson().toJson(newBidRequestModel);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), request);
        Log.d(tag, request);
        return client.newBid(headers, body);
    }

    public Observable<Response<NewBidResponseModel>> editBid(EditBidRequestModel editBidRequestModel){
        Map<String, String> headers = new HashMap<>();
        headers.put("auth-token", user.getAuthToken());
        String request = new Gson().toJson(editBidRequestModel);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), request);
        Log.d(tag, request);
        return client.editBid(headers, editBidRequestModel.getBidID(), body);
    }

    public Observable<Response<NewBidResponseModel>> deleteBid(int bidID){
        Map<String, String> headers = new HashMap<>();
        headers.put("auth-token", user.getAuthToken());
        return client.deleteBid(headers, bidID);
    }
}
