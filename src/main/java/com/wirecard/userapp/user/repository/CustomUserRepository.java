package com.wirecard.userapp.user.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.wirecard.userapp.user.entity.User;

public interface CustomUserRepository {

    public List<User> findUserByPredicate(Pageable pageable, Long id, String name, Date date, Long userType);
    
}
