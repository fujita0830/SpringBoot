package com.fujita.springboot.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table
public class Account {


	@OneToMany(cascade=CascadeType.ALL)
	@Column(nullable =true)
	private List<Contents> contents;


	public List<Contents> getContents() {
		return contents;
	}

	public void setContents(List<Contents> contents) {
		this.contents = contents;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long id;

	@Column(name="login_id",nullable=false,unique=true,length = 20)
	@Pattern(regexp="[0-9a-zA-Z]+",message="半角英数字のみで入力してください")
	private String loginId;

	@Column(name="login_password",nullable=false ,length = 20)
	@Pattern(regexp="[0-9a-zA-Z]+",message="半角英数字のみで入力してください")
	private String loginPassword;

	@Column(name = "login_flg")
	private String loginFlg;


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

	public String getLoginFlg() {
		return loginFlg;
	}

	public void setLoginFlg(String loginFlg) {
		this.loginFlg = loginFlg;
	}

}
