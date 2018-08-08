/**
Copyright: SYARS
2018

File Name: UserService.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/

package com.syars.attendance.service;

import java.util.Map;
import java.util.Set;

import com.syars.attendance.constants.AttendanceConstants;
import com.syars.attendance.dao.UserDao;
import com.syars.attendance.exceptions.DatabaseException;
import com.syars.attendance.vo.UserVO;

public class UserService {
	UserDao userDao = new UserDao();

	public String checkUserAuthorization(String userId, String password, Set<String> rolesSet) {

		//if user id, password and role defined matches then return passed
		UserVO user;
		try {
			user = userDao.getUserCredentials(userId);
			if(user == null) {
				return AttendanceConstants.INVALID_USER;
			}
			else {
				if (!password.equals(user.getPassword())) {
					return AttendanceConstants.INVALID_PASSWORD;
				}
				else {
					if(!rolesSet.contains(user.getUserRole())) {
						return AttendanceConstants.ROLE_NOT_ALLOWED;
					}
					else {
						return AttendanceConstants.PASSED;
					}
				}
			}
		} catch (DatabaseException e) {
			if(e.getMessage().equals(AttendanceConstants.MULTIPLE_USERS)) {
				return AttendanceConstants.MULTIPLE_USERS;
			}
			else if(e.getMessage().equals(AttendanceConstants.NO_PASSWORD)) {
				return AttendanceConstants.NO_PASSWORD;
			}
			return AttendanceConstants.DATABASE_EXCEPTION;
		}
	}

	public String registerMemberAsUser(UserVO userVo) {
		String createdUserId = null;
		try {
			createdUserId = userDao.registerMemberAsUser(userVo);
		} catch (DatabaseException e) {
			createdUserId = null;
		}
		return createdUserId;
	}

	public Map<String, UserVO> getAllUsers() {
		try {
			return userDao.getAllUsers();
		} catch (DatabaseException e) {
			System.out.println("DatabaseException occured:"+e.getMessage());
			return null;
		}
	}

	public UserVO getUser(String userId) {
		try {
			return userDao.getUser(userId);
		} catch (DatabaseException e) {
			System.out.println("DatabaseException occured:"+e.getMessage());
			return null;
		}
	}
	
	public String updateUser(UserVO userVo) {
		try {
			return userDao.updateUser(userVo);
		} catch (DatabaseException e) {
			//some logging
		}
		return null;
	}

	public int deleteMember(String userId) {
		int result = 1;
		try {
			userDao.deleteUser(userId);
		} catch (DatabaseException e) {
			result = 0;
		}
		return result;
	}

}
