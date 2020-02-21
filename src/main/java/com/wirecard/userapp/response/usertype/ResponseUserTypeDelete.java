package com.wirecard.userapp.response.usertype;

import com.wirecard.userapp.response.DefaultResponse;

public class ResponseUserTypeDelete extends DefaultResponse {

    private Long userTypeId;

    public ResponseUserTypeDelete(String status, Long userTypeId) {
        super(status);
        this.userTypeId = userTypeId;
    }

    public Long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
    }

}
