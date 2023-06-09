package com.sumit.blog1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumit.blog1.models.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
