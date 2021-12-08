package com.ubayKyu.accountingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountingNoteController {

	@RequestMapping("/AccountingList.html")
	public String AccountingList() {
		return "/AccountingList.html";
	}
	
	@RequestMapping("/AccountingDetail.html")
	public String AccountingDetail() {
		return "/AccountingDetail.html";
	}
}
