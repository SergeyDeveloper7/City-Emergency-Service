package com.sergeydeveloper7.data.models;

/**
 * Created by serg on 11.01.18.
 */

public class Request {

    private String customersName;
    private String customersNumber;
    private String customersAddress;
    private String customersDistrict;
    private String comments;
    private String workersName;

    public String getCustomersName() {
        return customersName;
    }

    public void setCustomersName(String customersName) {
        this.customersName = customersName;
    }

    public String getCustomersNumber() {
        return customersNumber;
    }

    public void setCustomersNumber(String customersNumber) {
        this.customersNumber = customersNumber;
    }

    public String getCustomersAddress() {
        return customersAddress;
    }

    public void setCustomersAddress(String customersAddress) {
        this.customersAddress = customersAddress;
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

    public String getWorkersName() {
        return workersName;
    }

    public void setWorkersName(String workersName) {
        this.workersName = workersName;
    }
}
