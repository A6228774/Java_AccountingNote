package com.ubayKyu.accountingSystem.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UserInfo")
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false, nullable = false, columnDefinition = "uniqueidentifier")
	public String id;
	@Column(name = "Account", nullable=false, unique=false, columnDefinition = "varchar(20)")
	public String account;
	@Column(name = "PWD", nullable=false, unique=false, columnDefinition = "varchar(50)")
	public String pwd;
	@Column(name = "Name", nullable=false, unique=false, columnDefinition = "varchar(20)")
	public String name;
	@Column(name = "Email", nullable=false, unique=false, columnDefinition = "nvarchar(100)")
	public String email;
	@Column(name = "UserLevel", nullable=false, unique=false)
	public Integer userLevel;
	@Column(name = "CreateDate", nullable=false, unique=false, columnDefinition = "datetime")
	public LocalDateTime createDate;
	@Column(name = "EditDate", nullable= true, unique=false, columnDefinition = "datetime")
	public LocalDateTime editDate;
	
	public UserInfo() {
		
	}

	public UserInfo(String id, String account, String pwd, String name, String email, Integer userLevel,
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
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
