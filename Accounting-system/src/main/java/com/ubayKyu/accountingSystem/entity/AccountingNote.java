package com.ubayKyu.accountingSystem.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AccountingNote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, columnDefinition = "int")
	public Integer id;
	@Column(name = "UserID", nullable = false, columnDefinition = "uniqueidentifier")
	public String userid;
	@Column(name = "Caption", nullable=false, unique=false, columnDefinition = "nvarchar(20)")
	public String caption;
	@Column(name = "Remarks", nullable=false, unique=false, columnDefinition = "nvarchar(500)")
	public String remarks;
	@Column(name = "Amount", nullable=false, unique=false, columnDefinition = "int")
	public Integer amount;
	@Column(name = "ActType", nullable=false, unique=false, columnDefinition = "int")
	public Integer actType;
	@Column(name = "CreateDate", nullable=false, unique=false, columnDefinition = "datetime")
	public LocalDateTime createDate;
	@Column(name = "CategoryID", nullable=true, unique=false, columnDefinition = "int")
	public Integer categoryid;
	
	public String title;
		
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getActType() {
		return actType;
	}
	public void setActType(Integer actType) {
		this.actType = actType;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	public Integer getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
