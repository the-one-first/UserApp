package com.wirecard.userapp.response.error;

import java.util.List;

import com.wirecard.userapp.response.DefaultResponse;

public class ResponseError extends DefaultResponse {

    private List<CodeDescError> details;

    public ResponseError(String status, List<CodeDescError> details) {
        super(status);
        this.details = details;
    }

    public List<CodeDescError> getDetails() {
        return details;
    }

    public void setDetails(List<CodeDescError> details) {
        this.details = details;
    }

}
