package com.ubayKyu.accountingSystem.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.repository.UserInfoRepository;

@Controller
public class UserInfoController {
	@Autowired
	private UserInfoRepository repo;

	@GetMapping("/UserProfile.html")
	public String UserProfileInfo(@RequestParam(value = "account", required = true) String acctxt, Model model,
			HttpSession session, HttpServletResponse response) throws IOException {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");
		if (currentUser == null) {
			response.sendRedirect("/Default.html");
		} else {
			UserInfo info = repo.findByAccountSQL(acctxt);

			model.addAttribute("ACC", info.account.toString());
			model.addAttribute("Name", info.name.toString());
			model.addAttribute("Email", info.getEmail().toString());
		}

		boolean IsAdmin;
		if (currentUser.getUserLevel() == 0) {
			IsAdmin = true;
		} else {
			IsAdmin = false;
		}
		model.addAttribute("level", IsAdmin);
		return "UserProfile.html";
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