package com.ubayKyu.accountingSystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
	
	@RequestMapping("/CategoryList.html")
	public String CategoryList() {
		return "/CategoryList.html";
	}
	
	@RequestMapping("/CategoryDetail.html")
	public String CategoryDetail() {
		return "/CategoryDetail.html";
	}
}
