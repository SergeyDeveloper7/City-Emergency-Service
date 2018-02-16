package com.sergeydeveloper7.cityemergencyservice.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by serg on 13.11.17.
 */

public class ErrorResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("source")
    @Expose
    private Source source;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("detail")
    @Expose
    private String detail;

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public Source getSource() {
        return source;
    }

    public class Source {

        @SerializedName("pointer")
        @Expose
        private String pointer;

        public String getPointer() {
            return pointer;
        }
    }
}
