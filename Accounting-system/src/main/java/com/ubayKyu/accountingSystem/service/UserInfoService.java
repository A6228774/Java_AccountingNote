package com.ubayKyu.accountingSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	//public List<UserInfo> getUserInfo() {
	//	return repository.findAll();
	//}
    //
	//public UserInfo saveUserInfo(UserInfo UserInfo) {
	//	return repository.save(UserInfo);
	//}
    
	public UserInfo getUserInfoByAccount(String acc) {
		return repository.findByAccountSQL(acc);
	}
	
	public UserInfo getLoginUser(String acc, String pwd) {
		return repository.findAllByaccountAndPWDSQL(acc, pwd);
	}

	//public String deleteUserInfo(UUID id) {
	//	repository.deleteById(id);
	//	return "Deleted!";
	//}
}   //
