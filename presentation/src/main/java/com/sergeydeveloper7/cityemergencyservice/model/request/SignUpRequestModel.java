package com.sergeydeveloper7.cityemergencyservice.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by serg on 06.02.18.
 */

public class SignUpRequestModel {

    @SerializedName("username")
    @Expose
    private String username = "";

    @SerializedName("password")
    @Expose
    private String password = "";

    @SerializedName("first_name")
    @Expose
    private String firstName = "";

    @SerializedName("last_name")
    @Expose
    private String lastName = "";

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber = "";

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
