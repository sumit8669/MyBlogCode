package com.sumit.blog1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumit.blog1.models.User;

public interface UserRepo  extends JpaRepository<User, Integer>{

}
