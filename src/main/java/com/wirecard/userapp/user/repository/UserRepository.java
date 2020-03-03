package com.wirecard.userapp.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wirecard.userapp.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {

}
