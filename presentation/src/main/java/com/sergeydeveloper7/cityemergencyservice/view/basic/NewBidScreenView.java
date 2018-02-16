package com.sergeydeveloper7.cityemergencyservice.view.basic;

/**
 * Created by serg on 06.02.18.
 */

public interface NewBidScreenView {
    void beforeRequest();
    void requestRuntimeError(String error);
    void newBidResponse(boolean isSuccess);
    void connectionErrorResponse();
}
