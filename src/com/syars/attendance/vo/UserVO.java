package com.syars.attendance.vo;

import org.apache.wink.common.model.atom.AtomLink;

public class UserVO extends MemberVO{
	private AtomLink link;
	private String userId;
	private String password;
	private String role;
	
	public UserVO() {
		super();
	}

	public UserVO(AtomLink link, String userId, String password, String role) {
		super();
		this.link = link;
		this.userId = userId;
		this.password = password;
		this.role = role;
	}

	public AtomLink getLink() {
		return link;
	}

	public void setLink(AtomLink link) {
		this.link = link;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
