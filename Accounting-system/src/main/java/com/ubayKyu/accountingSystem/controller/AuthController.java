package com.ubayKyu.accountingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes({"currentUser"})
@RestController
public class AuthController {
	private org.springframework.security.core.Authentication auth;
	
	@Autowired 
	 UserDetailsService userDetailsService;
	  
	 @GetMapping("/UserProfile.html")
	 public String authHome(Model model) {
	  auth = SecurityContextHolder.getContext().getAuthentication();
	  model.addAttribute("accname", auth.getName())
	   .addAttribute("roles", auth.getAuthorities());
	  return "/UserProfile.html";
	 }
}
