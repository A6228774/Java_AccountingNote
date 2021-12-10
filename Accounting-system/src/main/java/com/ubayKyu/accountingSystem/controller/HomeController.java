package com.ubayKyu.accountingSystem.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ubayKyu.accountingSystem.service.AccountingNoteService;
import com.ubayKyu.accountingSystem.service.UserInfoService;

@Controller
public class HomeController {
	
	@Autowired
	private UserInfoService userinfoSer;
	@Autowired
	private AccountingNoteService accountingnoteSer;
	
	@RequestMapping("/Default.html")
	public String Default() {
		return "/Default.html";
	}
	
	@GetMapping("/Default.html")
	public String MainPage(Model model) {
		model.addAttribute("first", accountingnoteSer.firstrecord().toString());
		model.addAttribute("last", accountingnoteSer.lastrecord().toString());
		model.addAttribute("notecnt", accountingnoteSer.notecnt());
        model.addAttribute("usercnt", userinfoSer.usercnt());
        return "Default.html";
    }
	
	@RequestMapping("/Login.html")
	public String Login() {
		return "/Login.html";
	}
}
