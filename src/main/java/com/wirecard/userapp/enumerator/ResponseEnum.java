package com.wirecard.userapp.enumerator;

public enum ResponseEnum {

    ERROR_STATUS("Error"),
    FAILED_INSERT("INS_999", "Failed Insert"),
    FAILED_UPDATE_NOT_FOUND("UPD_998", "Failed Update because existing data cannot be found"),
    FAILED_UPDATE("UPD_999", "Failed Update"),
    FAILED_DELETE("DEL_999", "Failed Delete"),
    SUCCESS_STATUS("Success");
    
    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return this.desc;
    }
    
    private ResponseEnum(String code) {
        this.code = code;
    }

    private ResponseEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
