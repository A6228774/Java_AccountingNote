package com.ubayKyu.accountingSystem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ubayKyu.accountingSystem.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	@Query(value = "SELECT * from Category WHERE UserID=?1", nativeQuery = true)
	List<Category> findByUserIDSQL(String userid, Pageable pageable);
	
	@Query(value = "SELECT COUNT(*) as CNT from Category WHERE UserID=?1", nativeQuery = true)
	Integer findTotalCategoryByUserIDSQL(String userid);
	
	@Query(value = "SELECT COUNT(*) as CNT from AccountingNote WHERE CategoryID=?1", nativeQuery = true)
	Integer AccountingInCategory(Integer cid);
	
	@Query(value = "DELETE FROM Category WHERE CategoryID=?1", nativeQuery = true)
	Category DeleteCategoryByCID(Integer cid);
}
