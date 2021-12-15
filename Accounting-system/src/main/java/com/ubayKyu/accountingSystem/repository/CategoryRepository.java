package com.ubayKyu.accountingSystem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ubayKyu.accountingSystem.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	@Query(value = "SELECT * from Category WHERE UserID=?1", nativeQuery = true)
	List<Category> findByUserIDSQL(String userid);

}
