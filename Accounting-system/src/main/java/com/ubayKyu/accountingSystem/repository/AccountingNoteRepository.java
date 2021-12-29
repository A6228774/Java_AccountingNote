package com.ubayKyu.accountingSystem.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ubayKyu.accountingSystem.entity.AccountingNote;
import com.ubayKyu.accountingSystem.entity.Category;

public interface AccountingNoteRepository
		extends JpaRepository<AccountingNote, Integer>, JpaSpecificationExecutor<AccountingNote> {

	@Query(value = "SELECT COUNT(*) as notecnt FROM AccountingNote", nativeQuery = true)
	Long Notecnt();

	@Query(value = "SELECT TOP 1 CreateDate FROM AccountingNote ORDER BY CreateDate ASC", nativeQuery = true)
	LocalDateTime first();

	@Query(value = "SELECT TOP 1 CreateDate FROM AccountingNote ORDER BY CreateDate DESC", nativeQuery = true)
	LocalDateTime last();

	@Query(value = "SELECT COUNT(CategoryID) as cnt FROM AccountingNote WHERE CategoryID=?1", nativeQuery = true)
	Integer checkaccountCNT(Integer cid);

	@Query(value = "SELECT * from AccountingNote WHERE UserID=?1", nativeQuery = true)
	List<AccountingNote> findByUserIDSQL(String userid, Pageable pageable);

	@Query(value = "SELECT AccountingNote.[ID], AccountingNote.[UserID], AccountingNote.[Caption], AccountingNote.[Remarks], AccountingNote.[Amount], AccountingNote.[ActType],"
			+ "AccountingNote.[CreateDate], AccountingNote.[CategoryID], Category.[Title]"
			+ "FROM [AccountingNote] LEFT JOIN [Category]"
			+ "ON AccountingNote.[CategoryID] = Category.[CategoryID] WHERE AccountingNote.[UserID]=?1", nativeQuery = true)
	List<AccountingNote> findNoteByUserIDSQL(String userid, Pageable pageable);

	@Query(value = "SELECT COUNT(*) as cnt from AccountingNote WHERE UserID=?1", nativeQuery = true)
	Integer findTotalAccountingByUserIDSQL(String userid);

	@Query(value = "DELETE FROM AccountingNote WHERE ID=?1", nativeQuery = true)
	public void DeleteAccountingByID(Integer accountingid);

	@Query(value = "SELECT SUM(Amount) as Income FROM AccountingNote WHERE UserID=?1 AND ActType=?2", nativeQuery = true)
	BigDecimal findTotalIncomeByUIDSQL(String userid, Integer act);

	@Query(value = "SELECT SUM(Amount) as Expentiture FROM AccountingNote WHERE UserID=?1 AND ActType=?2", nativeQuery = true)
	BigDecimal findTotalExpentitureByUIDSQL(String userid, Integer act);

	@Query(value = "SELECT SUM(Amount) as Expentiture FROM AccountingNote WHERE UserID=?1 AND ActType=?2 AND CreateDate BETWEEN ?3 AND ?4", nativeQuery = true)
	BigDecimal findMonthlyIncomeByUIDSQL(String userid, Integer act, LocalDate start, LocalDate end);

	@Query(value = "SELECT SUM(Amount) as Expentiture FROM AccountingNote WHERE UserID=?1 AND ActType=?2 AND CreateDate BETWEEN ?3 AND ?4", nativeQuery = true)
	BigDecimal findMonthlyExpentitureByUIDSQL(String userid, Integer act, LocalDate start, LocalDate end);

	@Query(value = "SELECT * FROM AccountingNote WHERE ID=?1", nativeQuery = true)
	AccountingNote findAccountingByID(Integer accountingid);
	
	@Transactional
    @Modifying
	@Query(value = "UPDATE AccountingNote SET UserID=?1, Caption=?2, Remarks=?3, Amount=?4, ActType=?5, CategoryID=?6 WHERE ID=?7", nativeQuery = true)
	public void UpdateAccountingByID(String useridtxt, String Captiontxt, String remarkstxt, Integer amount, Integer acttype, Integer cid, Integer aid);

	@Transactional
    @Modifying
	@Query(value = "INSERT INTO AccountingNote (UserID, Caption, Remarks, Amount, ActType, CreateDate, CategoryID) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)", nativeQuery = true)
	public void createAccounting(String uidtxt, String captiontxt, String remarkstxt, Integer amount, Integer act,
			LocalDateTime ct, Integer cidtxt);
}
