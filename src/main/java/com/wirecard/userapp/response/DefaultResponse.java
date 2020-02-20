package com.wirecard.userapp.response;

public abstract class DefaultResponse {

    private String status;

    public DefaultResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
