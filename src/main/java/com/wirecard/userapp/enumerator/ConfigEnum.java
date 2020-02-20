package com.wirecard.userapp.enumerator;

public enum ConfigEnum {

    CONTROLLER_PACKAGE("com.wirecard.userapp.controller"),
    DESCENDING("DESC");
    
    private String desc;

    public String getDesc() {
        return this.desc;
    }
    
    private ConfigEnum(String desc) {
        this.desc = desc;
    }
    
}
