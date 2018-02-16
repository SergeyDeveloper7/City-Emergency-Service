package com.sergeydeveloper7.cityemergencyservice.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sergeydeveloper7.cityemergencyservice.model.general.Bid;

/**
 * Created by serg on 08.02.18.
 */

public class NewBidResponseModel {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("body")
    @Expose
    private Bid bid;

    public boolean isSuccess() {
        return success;
    }

    public Bid getBid() {
        return bid;
    }
}
