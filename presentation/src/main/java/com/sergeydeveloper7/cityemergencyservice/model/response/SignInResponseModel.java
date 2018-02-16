package com.sergeydeveloper7.cityemergencyservice.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sergeydeveloper7.cityemergencyservice.model.general.User;

/**
 * Created by serg on 06.02.18.
 */

public class SignInResponseModel {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("body")
    @Expose
    private Body body;

    public boolean isSuccess() {
        return success;
    }

    public Body getBody() {
        return body;
    }

    public class Body {

        @SerializedName("user")
        @Expose
        private User user;

        @SerializedName("token")
        @Expose
        private String token;

        @SerializedName("message")
        @Expose
        private String message;

        public User getUser() {
            return user;
        }

        public String getToken() {
            return token;
        }

        public String getMessage() {
            return message;
        }
    }
}