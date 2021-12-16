package com.ubayKyu.accountingSystem.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.service.AccountingNoteService;
import com.ubayKyu.accountingSystem.service.UserInfoService;

@Controller
public class HomeController {

	@Autowired
	private UserInfoService service;
	@Autowired
	private AccountingNoteService accountingnoteSer;

	@RequestMapping("/Default.html")
	public String Default() {
		return "/Default.html";
	}

	@GetMapping("/Default.html")
	public String MainPage(Model model, HttpSession session) {
		
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");
		if (currentUser != null) {
			model.addAttribute("redirect", "/UserProfile.html?account=" + currentUser.getAccount());
		}
		else
		{
			model.addAttribute("redirect", "/Login.html");
		}
		model.addAttribute("first", accountingnoteSer.firstrecord().toString());
		model.addAttribute("last", accountingnoteSer.lastrecord().toString());
		model.addAttribute("notecnt", accountingnoteSer.notecnt());
		model.addAttribute("usercnt", service.usercnt());
		return "Default.html";
	}

	@RequestMapping("/Login.html")
	public String Login() {
		return "/Login.html";
	}
}
