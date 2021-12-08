package com.ubayKyu.accountingSystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.ubayKyu.accountingSystem.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {

	@Query(value = "SELECT COUNT(*) as usercnt FROM UserInfo", nativeQuery = true)
	Long Usercnt();

	@Query(value = "SELECT * from UserInfo WHERE Account='admin'", nativeQuery = true)
	UserInfo findByAccount(String acc);
}
