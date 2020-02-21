package com.wirecard.userapp.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.wirecard.userapp.validator.UserTypeId;

public class UserDTO {

    private Long id;
    
    @NotEmpty(message = "should not be empty")
    @Length(min = 1, max = 100)
    private String userName;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date userDate;
    
    @UserTypeId
    private Long userTypeId;

    public UserDTO(Long id, String userName, Date userDate, Long userTypeId) {
        this.id = id;
        this.userName = userName;
        this.userDate = userDate;
        this.userTypeId = userTypeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getUserDate() {
        return userDate;
    }

    public void setUserDate(Date userDate) {
        this.userDate = userDate;
    }

    public Long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
    }
    
}
