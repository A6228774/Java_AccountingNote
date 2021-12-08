package com.ubayKyu.accountingSystem.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="UserInfo")
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public UUID id;
	@Column(name = "Account")
	public String account;
	@Column(name = "PWD")
	public String pwd;
	@Column(name = "Name")
	public String name;
	@Column(name = "Email")
	public String email;
	@Column(name = "UserLevel")
	public Integer userLevel;
	@Column(name = "CreateDate")
	public LocalDateTime createDate;
	@Column(name = "EditDate", nullable= true)
	public LocalDateTime editDate;
	
	public UserInfo() {
		
	}

	public UserInfo(UUID id, String account, String pwd, String name, String email, Integer userLevel,
			LocalDateTime createDate, LocalDateTime editDate) {
		super();
		this.id = id;
		this.account = account;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
		this.userLevel = userLevel;
		this.createDate = createDate;
		this.editDate = editDate;
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getEditDate() {
		return editDate;
	}

	public void setEditDate(LocalDateTime editDate) {
		this.editDate = editDate;
	}
	
}