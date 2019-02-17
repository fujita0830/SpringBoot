package com.fujita.test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="login_userinfo")
public class LoginUser {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long id;

	@Column(name="login_id",nullable=true)
	private String loginId;

	@Column(name="login_pass",nullable=true)
	private String loginPassword;

	@Column(name="user_name",nullable=true)
	private String userName;



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


}
