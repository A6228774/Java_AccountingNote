package com.ubayKyu.accountingSystem.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubayKyu.accountingSystem.entity.Category;
import com.ubayKyu.accountingSystem.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository repository;

	public List<Category> getCategoryListByAccount(String userid) {
		return repository.findByUserIDSQL(userid);
	}
}
