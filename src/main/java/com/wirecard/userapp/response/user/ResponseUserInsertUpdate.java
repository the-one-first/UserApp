package com.wirecard.userapp.response.user;

import com.wirecard.userapp.response.DefaultResponse;

public class ResponseUserInsertUpdate extends DefaultResponse {
    
    private String userName;

    public ResponseUserInsertUpdate(String status, String userName) {
        super(status);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
