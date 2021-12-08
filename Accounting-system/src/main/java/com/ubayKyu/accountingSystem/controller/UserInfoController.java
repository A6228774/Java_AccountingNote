package com.ubayKyu.accountingSystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

	@RequestMapping("/UserProfile.html")
	public String UserProfile() {
		return "/UserProfile.html";
	}
	
	@RequestMapping("/UserList.html")
	public String UserList() {
		return "/UserList.html";
	}
	
	@RequestMapping("/UserDetail.html")
	public String UserDetail() {
		return "/UserDetail.html";
	}
}