package com.sergeydeveloper7.cityemergencyservice.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by serg on 06.02.18.
 */

public class SignUpResponseModel {

    @SerializedName("success")
    @Expose
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

}