package com.wirecard.userapp.response.usertype;

import com.wirecard.userapp.response.DefaultResponse;

public class ResponseUserTypeInsertUpdate extends DefaultResponse {
    
    private String userTypeName;

    public ResponseUserTypeInsertUpdate(String status, String userTypeName) {
        super(status);
        this.userTypeName = userTypeName;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

}
