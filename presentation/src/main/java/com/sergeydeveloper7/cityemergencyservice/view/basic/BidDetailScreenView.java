package com.sergeydeveloper7.cityemergencyservice.view.basic;

import com.sergeydeveloper7.cityemergencyservice.model.general.Bid;

/**
 * Created by serg on 06.02.18.
 */

public interface BidDetailScreenView {
    void beforeRequest();
    void requestRuntimeError(String error);
    void editBidResponse(boolean isSuccess, Bid bid);
    void deleteBidResponse(boolean isSuccess, Bid bid);
    void connectionErrorResponse();
}
