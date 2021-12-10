package com.ubayKyu.accountingSystem.Handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Component;

import com.ubayKyu.accountingSystem.Model.AuthUser;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User authuser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("currentUser", authuser);
        
        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);

        //登入後導向
        response.sendRedirect("/UserProfile.html?account=" + authuser.getUsername());
    }
}
