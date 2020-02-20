package com.wirecard.userapp.usertype.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.wirecard.userapp.usertype.entity.UserType;

public interface CustomUserTypeRepository {

    public List<UserType> findUserTypeByPredicate(Pageable pageable, Long id, String userTypeName);

}
