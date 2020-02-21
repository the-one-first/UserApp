package com.wirecard.userapp.response.user;

import com.wirecard.userapp.response.DefaultResponse;

public class ResponseUserDelete extends DefaultResponse {

    private Long userId;

    public ResponseUserDelete(String status, Long userId) {
        super(status);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
