package com.ubayKyu.accountingSystem.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ubayKyu.accountingSystem.entity.Category;
import com.ubayKyu.accountingSystem.service.CategoryService;


@Controller
public class CategoryController {
	@Autowired
	private CategoryService service;
	
	@GetMapping("/CategoryList.html")
	public String CategoryList(@RequestParam(value = "uid", required=true) String useridtxt, Model model) {
		List<Category> categorylist = service.getCategoryListByAccount(useridtxt);
		model.addAttribute("category", categorylist);
		return "/CategoryList.html";
	}
	
	@PostMapping("/CategoryDetail.html")
	public String newCategory() {
		
		return "/CategoryDetail.html";
	}
	
	@RequestMapping("/CategoryDetail.html")
	public String CategoryDetail() {
		return "/CategoryDetail.html";
	}
}
