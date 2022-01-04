package com.ubayKyu.accountingSystem.repository;

import java.time.LocalDateTime;
import java.util.List;

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

	@Query(value = "SELECT * FROM Category WHERE CategoryID=?1", nativeQuery = true)
	Category findCategoryByCID(Integer cid);
	
	@Query(value = "SELECT * FROM Category WHERE UserID=?1", nativeQuery = true)
	List<Category> findCategoryDDLByUserID(String userid);

	@Transactional
    @Modifying
	@Query(value = "UPDATE Category SET Title=?1, Remarks=?2, CreateDate=?3 WHERE CategoryID=?4", nativeQuery = true)
	public void UpdateCategoryByCID(String titletxt, String remarkstxt, LocalDateTime ct, Integer cid);
	
	@Query(value = "SELECT Top 1 CategoryID FROM Category ORDER BY CategoryID DESC", nativeQuery = true)
	Integer lastCategory();
	
	@Transactional
    @Modifying
	@Query(value = "INSERT INTO Category (UserID, Title, Remarks, CreateDate) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
	public void CreateCategoryByCID(String userid, String titletxt, String remarkstxt, LocalDateTime ct);
	
	@Query(value = "SELECT COUNT(Title) FROM Category WHERE Title=?1 AND UserID=?2", nativeQuery = true)
	Integer IsRepeatTitle(String Title, String userid);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM Category WHERE UserID=?1", nativeQuery = true)
	void DeleteAllCategoryByUserID(String indextxt);

	@Query(value ="SELECT TOP 1 CategoryID FROM Category WHERE UserID=?1 ORDER BY CreateDate DESC", nativeQuery = true)	
	Integer findLastCategoryByUID(String uidtxt);
}
