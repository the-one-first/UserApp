package com.wirecard.userapp.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class UserTypeDTO {
    
    private Long id;
    
    @NotEmpty(message = "should not be empty")
    @Length(min = 1, max = 100)
    private String name;

    public UserTypeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
