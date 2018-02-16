package com.sergeydeveloper7.cityemergencyservice.view.basic;

/**
 * Created by serg on 06.02.18.
 */

public interface RegisterScreenView {
    void beforeRequest();
    void requestRuntimeError(String error);
    void afterRequest(boolean successful);
    void connectionErrorResponse();
}
