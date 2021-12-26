package com.ubayKyu.accountingSystem.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ubayKyu.accountingSystem.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>,JpaSpecificationExecutor<Category>
{

	@Query(value = "SELECT * from Category WHERE UserID=?1", nativeQuery = true)
	List<Category> findByUserIDSQL(String userid, Pageable pageable);

	@Query(value = "SELECT COUNT(*) as CNT from Category WHERE UserID=?1", nativeQuery = true)
	Integer findTotalCategoryByUserIDSQL(String userid);

	@Query(value = "SELECT COUNT(*) as CNT from AccountingNote WHERE CategoryID=?1", nativeQuery = true)
	Integer AccountingInCategory(Integer cid);

	//@Query(value = "DELETE FROM Category WHERE CategoryID=?1", nativeQuery = true)
	//public void DeleteCategoryByCID(Integer cid);

	@Query(value = "SELECT * FROM Category WHERE CategoryID=?1", nativeQuery = true)
	Category findCategoryByCID(Integer cid);
	
	@Query(value = "SELECT * FROM Category WHERE UserID=?1", nativeQuery = true)
	List<Category> findCategoryDDLByUserID(String userid);

	@Query(value = "UPDATE Category SET Title=?1, Remarks=?2 WHERE CategoryID=?3", nativeQuery = true)
	public void UpdateCategoryByCID(String titletxt, String remarkstxt, Integer cid);
	
	@Query(value = "SELECT Top 1 CategoryID FROM Category ORDER BY CategoryID DESC", nativeQuery = true)
	Integer lastCategory();
	
	@Query(value = "INSERT INTO Category (UserID, Title, Remarks, CreateDate) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
	Category CreateCategoryByCID(String userid, String titletxt, String remarkstxt, LocalDateTime ct);
}
