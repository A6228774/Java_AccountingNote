package com.ubayKyu.accountingSystem.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.service.UserInfoService;

@RestController
public class UserInfoController {

	@Autowired
	private UserInfoService userinfoSer;

	//@GetMapping("/UserProfile.html")
	//public UserInfo info(@RequestParam(name = "account") String acc, Model model) {
	//	UserInfo infodata = userinfoSer.getUserInfoByAccount(acc);
    //
	//	model.addAttribute("ACC", infodata.getAccount().toString());
	//	model.addAttribute("Name", infodata.getName().toString());
	//	model.addAttribute("Email", infodata.getEmail().toString());
	//	return info("/UserProfile.html?account=" + acc, model);
	//}
}