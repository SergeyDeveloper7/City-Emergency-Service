package com.sergeydeveloper7.cityemergencyservice.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hotyun_a on 04.08.17.
 */

public class Error {

    @SerializedName("errors")
    @Expose
    private List<ErrorResponse> errors = null;

    public List<ErrorResponse> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorResponse> errors) {
        this.errors = errors;
    }

}
