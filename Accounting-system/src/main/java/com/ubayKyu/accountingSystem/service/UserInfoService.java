package com.ubayKyu.accountingSystem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.repository.UserInfoRepository;

@Service
public class UserInfoService {
	@Autowired
	private UserInfoRepository repository;

	public Long usercnt() {
		return repository.Usercnt();
	}

	public UserInfo getUserInfoByAccount(String acc) {
		return repository.findByAccountSQL(acc);
	}

	public UserInfo getLoginUser(String acc, String pwd) {
		return repository.findAllByaccountAndPWDSQL(acc, pwd);
	}

	public void UpdateUserInfo(String nametxt, String emailtxt, String useridtxt) throws Exception {
		if (nametxt.length() > 20) {
			throw new Exception("姓名長度應在20以內");
		}
		if (emailtxt.length() > 100) {
			throw new Exception("Email長度應在100以內");
		}
		repository.UpdateUserInfoByID(nametxt, emailtxt, useridtxt);
	}

	public List<UserInfo> getAllUserList(Pageable pageable) {
		return repository.findAllUserListSQL();
	}

	public UserInfo getUserInfoByUserID(String userid) {
		return repository.findByUserIDSQL(userid);
	}

	public void updateUser_Admin(String nametxt, String emailtxt, Integer level, LocalDateTime ct, String userid)
			throws Exception {
		if (nametxt.length() > 20) {
			throw new Exception("姓名長度應在20以內");
		}
		if (emailtxt.length() > 100) {
			throw new Exception("Email長度應在100以內");
		}
		repository.UpdateUser_Admin(nametxt, emailtxt, level, ct, userid);
	}
	// public String deleteUserInfo(String id) {
	// repository.deleteById(id);
	// return "Deleted!";
	// }

	public Boolean checkRepeat(String acctxt) {
		Integer cnt = repository.checkRepeat(acctxt);
		boolean Isrepeat;
		if (cnt > 0) {
			Isrepeat = true;
		} else {
			Isrepeat = false;
		}
		return Isrepeat;
	}

	public void createNewUser(String acctxt, String pwdtxt, String nametxt, String emailtxt, Integer level,
			LocalDateTime ct) throws Exception {
		if (acctxt.length() > 20) {
			throw new Exception("帳號長度應在20以內");
		}
		if (nametxt.length() > 20) {
			throw new Exception("姓名長度應在20以內");
		}
		if (emailtxt.length() > 100) {
			throw new Exception("Email長度應在100以內");
		}
		repository.createUser(acctxt, pwdtxt, nametxt, emailtxt, level, ct);
	}
}
