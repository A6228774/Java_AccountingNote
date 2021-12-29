package com.ubayKyu.accountingSystem.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.ubayKyu.accountingSystem.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {

	@Query(value = "SELECT COUNT(*) as usercnt FROM UserInfo", nativeQuery = true)
	Long Usercnt();

	@Query(value = "SELECT * from UserInfo WHERE Account=?1", nativeQuery = true)
	UserInfo findByAccountSQL(String acc);

	UserInfo findByAccount(String acc);

	@Query(value = "SELECT * from UserInfo WHERE Account=?1 AND PWD=?2", nativeQuery = true)
	UserInfo findAllByaccountAndPWDSQL(String acc, String pwd);

	@Transactional
	@Modifying
	@Query(value = "UPDATE UserInfo SET Name=?1, Email=?2 WHERE ID=?3", nativeQuery = true)
	public void UpdateUserInfoByID(String name, String email, String userid);

	@Query(value = "SELECT * FROM UserInfo", nativeQuery = true)
	List<UserInfo> findAllUserListSQL();

	@Query(value = "SELECT * from UserInfo WHERE ID=?1", nativeQuery = true)
	UserInfo findByUserIDSQL(String userid);

	@Transactional
	@Modifying
	@Query(value = "UPDATE UserInfo SET Name=?1, Email=?2, UserLevel=?3, EditDate=?4 WHERE ID=?5", nativeQuery = true)
	public void UpdateUser_Admin(String nametxt, String emailtxt, Integer level, LocalDateTime ct, String userid);

	@Query(value = "SELECT COUNT(Account) FROM UserInfo WHERE Account=?1", nativeQuery = true)
	Integer checkRepeat(String acctxt);

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO UserInfo (Account, PWD, Name, Email, UserLevel, CreateDate) VALUES (?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
	public void createUser(String acctxt, String pwdtxt, String nametxt, String emailtxt, Integer level, LocalDateTime ct);
}
