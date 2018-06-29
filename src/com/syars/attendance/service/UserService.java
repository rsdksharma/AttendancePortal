package com.syars.attendance.service;

import java.util.Set;

import com.syars.attendance.dao.UserDao;
import com.syars.attendance.vo.UserVO;

public class UserService {
	UserDao userDao = new UserDao();

	public String registerAsNormaluser(UserVO userVo) {
		String message = "User registered successfully.\nBranch UID Number is:" + userVo.getBranchUIDNumber()
				+ ".\nType of user is:" + userVo.getRole();
		return message;
	}

	public boolean checkUserAuthorization(String userId, String password, Set<String> rolesSet) {
		boolean isAllowed = false;

		// Step 1. Fetch password from database and match with password in argument
		// If both match then get the defined role for user from database and continue;
		// else return isAllowed [false]

		UserVO user = userDao.getUserCredentials(userId);
		if (password.equals(user.getPassword())) {
			// Step 2. Verify user role
			if (rolesSet.contains(user.getRole())) {
				return true;
			} else {
				// logger role not allowed
			}
		} else {
			// invalid password
		}

		return isAllowed;

	}

	public void createUser(UserVO userVo) {
		userDao.createUser(userVo);
		
	}

}
