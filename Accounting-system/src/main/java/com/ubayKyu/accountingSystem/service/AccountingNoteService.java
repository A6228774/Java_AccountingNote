package com.ubayKyu.accountingSystem.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ubayKyu.accountingSystem.entity.AccountingNote;
import com.ubayKyu.accountingSystem.repository.AccountingNoteRepository;

@Service
public class AccountingNoteService {
	@Autowired
	private AccountingNoteRepository repository;

	public Long notecnt() {
		return repository.Notecnt();
	}

	public LocalDateTime firstrecord() {
		return repository.first();
	}

	public LocalDateTime lastrecord() {
		return repository.last();
	}

	public Integer checkaccountCNT(Integer cid) {
		return repository.checkaccountCNT(cid);
	}

	public List<AccountingNote> getAccountingListByAccount(String userid, Pageable pageable) {
		return repository.findByUserIDSQL(userid, pageable);
	}

	public List<AccountingNote> getFullAccountingListByAccount(String userid, Pageable pageable) {
		return repository.findNoteByUserIDSQL(userid, pageable);
	}

	public Integer getTotalAccountingByUserID(String userid) {
		return repository.findTotalAccountingByUserIDSQL(userid);
	}

	public void DeleteAccountingByID(Integer accountingid) {
		repository.DeleteAccountingByID(accountingid);
		return;
	}

	public BigDecimal getTotalIncomeByUID(String userid, Integer act) {
		return repository.findTotalIncomeByUIDSQL(userid, act);
	}

	public BigDecimal getTotalExpentitureByUID(String userid, Integer act) {
		return repository.findTotalExpentitureByUIDSQL(userid, act);
	}

	public BigDecimal getMonthlyIncomeByUID(String userid, Integer act, LocalDate start, LocalDate end) {
		return repository.findMonthlyIncomeByUIDSQL(userid, act, start, end);
	}

	public BigDecimal getMonthlyExpentitureByUID(String userid, Integer act, LocalDate start, LocalDate end) {
		return repository.findMonthlyExpentitureByUIDSQL(userid, act, start, end);
	}

	public AccountingNote getAccountingByID(Integer accountingid) {
		return repository.findAccountingByID(accountingid);
	}

	public void updateAccountingByID(String useridtxt, String Captiontxt, String remarkstxt, Integer amount,
			Integer acttype, Integer cid, LocalDateTime ct, Integer aid) throws Exception {
		if (Captiontxt.length() > 20) {
			throw new Exception("標題字串長度大於20");
		}
		if (amount < 1 || amount > 10000000) {
			throw new Exception("金額應在一至一千萬以內");
		}
		repository.UpdateAccountingByID(useridtxt, Captiontxt, remarkstxt, amount, acttype, cid, ct, aid);
	}

	public void createAccountingBy(String uidtxt, String captiontxt, String remarkstxt, Integer amount, Integer act,
			LocalDateTime ct, Integer cidtxt) throws Exception {
		if (captiontxt.length() > 20) {
			throw new Exception("標題字串長度大於20");
		}
		if (amount < 1 || amount > 10000000) {
			throw new Exception("金額應在一至一千萬以內");
		}
		repository.createAccounting(uidtxt, captiontxt, remarkstxt, amount, act, ct, cidtxt);
	}

	public void DeleteAccountingByUserID(String useridtxt) {
		repository.DeleteAllAccountingByUserID(useridtxt);
		
	}

	public Integer getLastAccounting(String uidtxt) {
		return repository.findLastAccountingNote(uidtxt);
	}
}
