package com.wirecard.userapp.enumerator;

public enum ConfigEnum {

    CONTROLLER_PACKAGE("com.wirecard.userapp.controller"),
    DESCENDING("DESC"),
    DATE_FORMAT_MYSQL_COMPARE("yyyy-MM-dd"),
    DATE_FORMAT_H2_COMPARE("YYYY-MM-DD"),
    LOCAL_ENV("local"),
    SERVER_ENV("server");
    
    private String desc;

    public String getDesc() {
        return this.desc;
    }
    
    private ConfigEnum(String desc) {
        this.desc = desc;
    }
    
}
