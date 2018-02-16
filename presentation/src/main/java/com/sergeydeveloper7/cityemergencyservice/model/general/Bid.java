package com.sergeydeveloper7.cityemergencyservice.model.general;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by serg on 07.02.18.
 */

public class Bid {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("customers_name")
    @Expose
    private String customersName;

    @SerializedName("customers_phone")
    @Expose
    private String customersPhone;

    @SerializedName("customers_addres_title")
    @Expose
    private String customersAddressTitle;

    @SerializedName("customers_addres_latitude")
    @Expose
    private Double customersAddressLatitude;

    @SerializedName("customers_addres_longtitude")
    @Expose
    private Double customersAddressLongtitude;

    @SerializedName("customers_district")
    @Expose
    private String customersDistrict;

    @SerializedName("comments")
    @Expose
    private String comments;

    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCustomersName() {
        return customersName;
    }

    public String getCustomersPhone() {
        return customersPhone;
    }

    public String getCustomersAddressTitle() {
        return customersAddressTitle;
    }

    public Double getCustomersAddressLatitude() {
        return customersAddressLatitude;
    }

    public Double getCustomersAddressLongtitude() {
        return customersAddressLongtitude;
    }

    public String getCustomersDistrict() {
        return customersDistrict;
    }

    public String getComments() {
        return comments;
    }

    public String getStatus() {
        return status;
    }
}