package com.wirecard.userapp.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wirecard.userapp.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long>,
        JpaSpecificationExecutor<User>, CustomUserRepository {

}
