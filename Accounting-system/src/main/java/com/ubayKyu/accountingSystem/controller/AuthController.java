package com.ubayKyu.accountingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes({"currentUser"})
@RestController
public class AuthController {
	
	@Autowired 
	 UserDetailsService userDetailsService;
}
