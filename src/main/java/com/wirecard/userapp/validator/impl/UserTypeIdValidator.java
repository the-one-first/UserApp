package com.wirecard.userapp.validator.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.wirecard.userapp.usertype.entity.UserType;
import com.wirecard.userapp.usertype.service.UserTypeService;
import com.wirecard.userapp.validator.UserTypeId;

public class UserTypeIdValidator implements ConstraintValidator<UserTypeId, Long> {

    @Autowired
    private UserTypeService userTypeService;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {

        try {
            return isValidToInsertUserTypeId(value);
        } catch (Exception e) {
            return false;
        }

    }

    private boolean isValidToInsertUserTypeId(Long userTypeIdToInsert) {

        List<Long> userTypeIds = getUserTypesAllowedList();
        return userTypeIds.contains(userTypeIdToInsert);

    }

    private List<Long> getUserTypesAllowedList() {

        List<Long> resultUserTypesAllowedList = new ArrayList<>();

        List<UserType> availableUserTypesList = userTypeService.getAllUserTypeList();

        for (UserType loopUserType : availableUserTypesList) {
            resultUserTypesAllowedList.add(loopUserType.getId());
        }

        return resultUserTypesAllowedList;

    }
}
