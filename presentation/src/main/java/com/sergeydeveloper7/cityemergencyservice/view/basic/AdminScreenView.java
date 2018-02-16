package com.sergeydeveloper7.cityemergencyservice.view.basic;

import com.sergeydeveloper7.cityemergencyservice.model.general.Bid;

import java.util.ArrayList;

/**
 * Created by serg on 06.02.18.
 */

public interface AdminScreenView {
    void beforeRequest();
    void requestRuntimeError(String error);
    void getBidsRequest(ArrayList<Bid> bids);
    void connectionErrorResponse();
}
