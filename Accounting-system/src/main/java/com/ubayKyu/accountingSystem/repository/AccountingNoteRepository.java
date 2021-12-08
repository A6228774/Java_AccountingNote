package com.ubayKyu.accountingSystem.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ubayKyu.accountingSystem.entity.AccountingNote;

public interface AccountingNoteRepository extends JpaRepository<AccountingNote,Integer>{

	@Query(value = "SELECT COUNT(*) as notecnt FROM AccountingNote", nativeQuery = true)
    Long Notecnt();	
	
	@Query(value = "SELECT TOP 1 CreateDate FROM AccountingNote ORDER BY CreateDate ASC", nativeQuery = true)
	LocalDateTime first();	
	
	@Query(value = "SELECT TOP 1 CreateDate FROM AccountingNote ORDER BY CreateDate DESC", nativeQuery = true)
	LocalDateTime last();
}
