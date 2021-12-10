package com.ubayKyu.accountingSystem.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ubayKyu.accountingSystem.Model.AuthUser;
import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.repository.UserInfoRepository;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private UserInfoRepository userinfoRepo;
	
	 @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        UserInfo info = userinfoRepo.findByAccountSQL(username);
	        if (info == null) {
	            throw new UsernameNotFoundException("User not found");
	        }
	        String password = info.getPwd(); 
	        String encodepwd = new BCryptPasswordEncoder().encode(password);
	        Collection<GrantedAuthority> authList = getAuthorities();
	        
	        if(info.getUserLevel() == 0)
	        {
	        	String level = "ADMIN";
	        }
	        else
	        {
	        	String level = "NORMAL";
	        }
	        
	        return new User(username, encodepwd, true, true, true, true, authList);
	    }

	private Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(); 
		authList.add(new SimpleGrantedAuthority("ADMIN"));
		authList.add(new SimpleGrantedAuthority("NORMAL"));
		return authList; 
	}
}
