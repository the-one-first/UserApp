package com.wirecard.userapp.usertype.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wirecard.userapp.usertype.entity.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long>, PagingAndSortingRepository<UserType, Long>,
        JpaSpecificationExecutor<UserType>, CustomUserTypeRepository {

}
