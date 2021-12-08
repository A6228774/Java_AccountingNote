package com.ubayKyu.accountingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubayKyu.accountingSystem.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
