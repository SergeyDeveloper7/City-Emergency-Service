package com.sergeydeveloper7.cityemergencyservice.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sergeydeveloper7.cityemergencyservice.model.general.Bid;

import java.util.ArrayList;

/**
 * Created by serg on 06.02.18.
 */

public class GetBidsResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("body")
    @Expose
    private ArrayList<Bid> bids;

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }
}
