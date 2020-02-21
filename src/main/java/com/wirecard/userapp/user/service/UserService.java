package com.wirecard.userapp.user.service;

import java.util.Date;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.wirecard.userapp.response.DefaultResponse;
import com.wirecard.userapp.response.user.ResponseUserView;
import com.wirecard.userapp.user.entity.User;

public interface UserService {

    public ResponseEntity<ResponseUserView> getUserList(Pageable pageable, Long id, String name, Date date, Long userType);
    
    public ResponseEntity<DefaultResponse> insertNewUser(User user);
    
    public ResponseEntity<DefaultResponse> updateExistingUserById(Long id, User user);
    
    public ResponseEntity<DefaultResponse> deleteExistingUserById(Long id);
    
}
