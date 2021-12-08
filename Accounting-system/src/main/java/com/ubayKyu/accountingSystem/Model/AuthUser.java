package com.ubayKyu.accountingSystem.Model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.ubayKyu.accountingSystem.entity.UserInfo;

public class AuthUser implements UserDetails {
	private UserInfo userinfo;

	public AuthUser(UserInfo info) {
		this.userinfo = userinfo;
	}
	
	@Override
	public Collection<? extends GrantedAuthority>getAuthorities() {
        return null;
    }

	@Override
    public String getPassword() {
        return userinfo.getPwd();
    }
 
	@Override
	public String getUsername() {
		return userinfo.getAccount();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
 
}
