package com.wirecard.userapp.usertype.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.wirecard.userapp.response.DefaultResponse;
import com.wirecard.userapp.response.usertype.ResponseUserTypeView;
import com.wirecard.userapp.usertype.entity.UserType;

public interface UserTypeService {

    public ResponseEntity<ResponseUserTypeView> getUserTypeList(Pageable pageable, Long id, String userTypeName);
    
    public List<UserType> getAllUserTypeList();
    
    public ResponseEntity<DefaultResponse> insertNewUserType(UserType userType);
    
    public ResponseEntity<DefaultResponse> updateExistingUserTypeById(Long id, UserType userType);
    
    public ResponseEntity<DefaultResponse> deleteExistingUserTypeById(Long id);
    
}
