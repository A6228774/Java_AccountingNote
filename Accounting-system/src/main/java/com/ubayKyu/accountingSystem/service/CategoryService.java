package com.ubayKyu.accountingSystem.service;

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
}
