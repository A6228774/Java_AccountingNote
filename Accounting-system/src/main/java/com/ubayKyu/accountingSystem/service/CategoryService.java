package com.ubayKyu.accountingSystem.service;

import java.time.LocalDateTime;
import java.util.List;

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

	public void DeleteCategoryByCID(Integer cid) {
		repository.deleteById(cid);
		return;
	}

	public Category getCategoryByCID(Integer cid) {
		return repository.findCategoryByCID(cid);
	}

	public List<Category> getCategoryDLLByUserID(String userid) {
		return repository.findCategoryDDLByUserID(userid);
	}

	public void updateCategoryByCID(String titletxt, String remarkstxt, LocalDateTime ct, Integer cid) throws Exception {
		if (titletxt.length() > 20) {
			throw new Exception("標題字串長度大於20");
		}
		repository.UpdateCategoryByCID(titletxt, remarkstxt, ct, cid);
	}

	public Integer getlastCID() {
		return repository.lastCategory();
	}

	public void createCategoryByCID(String uidtxt, String titletxt, String remarkstxt, LocalDateTime ct)
			throws Exception {
		if (titletxt.length() > 20) {
			throw new Exception("標題字串長度大於20");
		}
		repository.CreateCategoryByCID(uidtxt, titletxt, remarkstxt, ct);
	}

	public Boolean checkRepeat(String titletxt, String userid) {
		Integer cnt = repository.IsRepeatTitle(titletxt, userid);
		boolean Isrepeat;
		if (cnt > 0) {
			Isrepeat = true;
		} else {
			Isrepeat = false;
		}
		return Isrepeat;
	}

	public void DeleteCategoryByUserID(String indextxt) {
		repository.DeleteAllCategoryByUserID(indextxt);
	}
}
