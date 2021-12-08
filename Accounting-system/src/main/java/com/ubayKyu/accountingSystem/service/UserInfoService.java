package com.ubayKyu.accountingSystem.service;

import java.util.List;
import java.util.UUID;

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
	//// test
	//public UserInfo saveUserInfo(UserInfo UserInfo) {
	//	return repository.save(UserInfo);
	//}
    
	public UserInfo getUserInfoByAccount(String acc) {
		return repository.findByAccount(acc);
	}

	//public String deleteUserInfo(UUID id) {
	//	repository.deleteById(id);
	//	return "Deleted!";
	//}
}   //
