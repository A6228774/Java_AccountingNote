package com.ubayKyu.accountingSystem.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CategoryID", updatable = false, nullable = false, columnDefinition = "int")
	public Integer categoryid;
	@Column(name = "UserID", nullable = false, columnDefinition = "uniqueidentifier")
	public String userid;
	@Column(name = "Title", nullable=false, unique=false, columnDefinition = "nvarchar(20)")
	public String title;
	@Column(name = "Remarks", nullable=true, unique=false, columnDefinition = "nvarchar(500)")
	public String remarks;
	@Column(name = "CreateDate", nullable=false, unique=false, columnDefinition = "datetime")
	public LocalDateTime createDate;

	public Integer getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
}
