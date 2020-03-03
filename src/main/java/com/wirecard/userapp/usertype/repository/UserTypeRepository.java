package com.wirecard.userapp.usertype.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wirecard.userapp.usertype.entity.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long>, CustomUserTypeRepository {

}
