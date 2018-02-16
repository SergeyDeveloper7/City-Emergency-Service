package com.sergeydeveloper7.cityemergencyservice.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by serg on 08.02.18.
 */

public class NewBidRequestModel {

    @SerializedName("title")
    @Expose
    private String title = "";

    @SerializedName("customers_name")
    @Expose
    private String customersName = "";

    @SerializedName("customers_phone")
    @Expose
    private String customersPhone = "";

    @SerializedName("customers_addres_title")
    @Expose
    private String customersAddressTitle = "";

    @SerializedName("customers_addres_latitude")
    @Expose
    private Double customersAddressLatitude;

    @SerializedName("customers_addres_longtitude")
    @Expose
    private Double customersAddressLongtitude;

    @SerializedName("customers_district")
    @Expose
    private String customersDistrict = "";

    @SerializedName("comments")
    @Expose
    private String comments = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomersName() {
        return customersName;
    }

    public void setCustomersName(String customersName) {
        this.customersName = customersName;
    }

    public String getCustomersPhone() {
        return customersPhone;
    }

    public void setCustomersPhone(String customersPhone) {
        this.customersPhone = customersPhone;
    }

    public String getCustomersAddressTitle() {
        return customersAddressTitle;
    }

    public void setCustomersAddressTitle(String customersAddressTitle) {
        this.customersAddressTitle = customersAddressTitle;
    }

    public Double getCustomersAddressLatitude() {
        return customersAddressLatitude;
    }

    public void setCustomersAddressLatitude(Double customersAddressLatitude) {
        this.customersAddressLatitude = customersAddressLatitude;
    }

    public Double getCustomersAddressLongtitude() {
        return customersAddressLongtitude;
    }

    public void setCustomersAddressLongtitude(Double customersAddressLongtitude) {
        this.customersAddressLongtitude = customersAddressLongtitude;
    }

    public String getCustomersDistrict() {
        return customersDistrict;
    }

    public void setCustomersDistrict(String customersDistrict) {
        this.customersDistrict = customersDistrict;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
