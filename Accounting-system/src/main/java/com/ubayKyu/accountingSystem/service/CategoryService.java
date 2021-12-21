package com.ubayKyu.accountingSystem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ubayKyu.accountingSystem.entity.Category;
import com.ubayKyu.accountingSystem.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository repository;

	public List<Category> getCategoryListByAccount(String userid, Pageable pageable) {
		return repository.findByUserIDSQL(userid, pageable);
	}

	public Integer getTotalCategoryByUserID(String userid) {
		return repository.findTotalCategoryByUserIDSQL(userid);
	}

	public Integer getAccountingInCategory(Integer cid) {
		return repository.AccountingInCategory(cid);
	}

	public Category DeleteCategoryByCID(Integer cid) {
		return repository.DeleteCategoryByCID(cid);
	}

	public Category getCategoryByCID(Integer cid) {
		return repository.findCategoryByCID(cid);
	}

	public void updateCategoryByCID(String titletxt, String remarkstxt, Integer cid) throws Exception {
		if (titletxt.length() > 20) {
			throw new Exception("標題字串長度大於20");
		}
		repository.UpdateCategoryByCID(titletxt, remarkstxt, cid);
	}

	public Integer getlastCID() {
		return repository.lastCategory();
	}

	public void createCategoryByCID(Integer newcid, String uidtxt, String titletxt, String remarkstxt, LocalDateTime ct)
			throws Exception {
		if (titletxt.length() > 20) {
			throw new Exception("標題字串長度大於20");
		}
		repository.CreateCategoryByCID(newcid, uidtxt, titletxt, remarkstxt, ct);
	}
}
