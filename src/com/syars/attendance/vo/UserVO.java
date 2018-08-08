/**
Copyright: SYARS
2018

File Name: UserVO.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/

package com.syars.attendance.vo;

public class UserVO {
	
	private String memberId;
	private String userId;
	private String password;
	private String userRole;
	private boolean userIdCustomized;
	private String customizedUserId;

	public UserVO() {
		super();
	}

	public UserVO(String userId, String password, String role) {
		super();
		this.userId = userId;
		this.password = password;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberID) {
		this.memberId = memberID;
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

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public boolean isUserIdCustomized() {
		return userIdCustomized;
	}

	public void setUserIdCustomized(boolean userIdCustomized) {
		this.userIdCustomized = userIdCustomized;
	}

	public String getCustomizedUserId() {
		return customizedUserId;
	}

	public void setCustomizedUserId(String customizedUserId) {
		this.customizedUserId = customizedUserId;
	}

}
