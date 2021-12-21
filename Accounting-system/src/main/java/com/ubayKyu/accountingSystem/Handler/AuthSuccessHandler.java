package com.ubayKyu.accountingSystem.Handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.repository.UserInfoRepository;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserInfoRepository repository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// 加入Session
		HttpSession session = request.getSession();
		User auth = (User) authentication.getPrincipal();
		UserInfo loginuser = repository.findByAccount(auth.getUsername());
		session.setAttribute("loginUser", loginuser);

		// set response to OK status
		response.setStatus(HttpServletResponse.SC_OK);

		// 登入後導向
		response.sendRedirect("/UserProfile.html?account=" + auth.getUsername());
	}
}
