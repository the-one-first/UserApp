package com.wirecard.userapp.enumerator;

public enum ErrorEnum {

    ERR_FIELD("FLD_ERR"),
    ERR_OBJECT("OBJ_ERR"),
    ERR_USER_NM_UNIQUE("USR_001", "User Name Is Already Exist"),
    ERR_MSG_NOT_READABLE("MNR_ERR"),
    ERR_METHOD_ARG_TYP_MISMATCH("MAT_ERR"),
    ERR_UNEXPECTED_ROLLBACK("UER_ERR", "Unexpected Rollback Exception"),
    ERR_DATA_INTEGRATION("DTI_ERR", "Data Integration Exception"),
    ERR_CONSTRAINT_VIOLATION("CSV_ERR"),
    ERR_TIMEDOUT_OUT("TTO_ERR", "Transaction Timed Out Exception"),
    ERR_USER_TYPE_NM_UNIQUE("UST_001", "User Type Name Is Already Exist");
    
    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return this.desc;
    }
    
    private ErrorEnum(String code) {
        this.code = code;
    }

    private ErrorEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    
}
