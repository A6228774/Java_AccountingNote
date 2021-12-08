package com.ubayKyu.accountingSystem.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AccountingNote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	@Column(name = "UserID")
	public UUID userid;
	@Column(name = "Caption")
	public String caption;
	@Column(name = "Remarks")
	public String remarks;
	@Column(name = "Amount")
	public Integer amount;
	@Column(name = "ActType")
	public Integer actType;
	@Column(name = "CreateDate")
	public LocalDateTime createDate;
	@Column(name = "CategoryID")
	public Integer categoryid;
		
	public AccountingNote(Integer id, UUID userid, String caption, String remarks, Integer amount, Integer actType,
			LocalDateTime createDate, Integer categoryid) {
		super();
		this.id = id;
		this.userid = userid;
		this.caption = caption;
		this.remarks = remarks;
		this.amount = amount;
		this.actType = actType;
		this.createDate = createDate;
		this.categoryid = categoryid;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public UUID getUserid() {
		return userid;
	}
	public void setUserid(UUID userid) {
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
}
