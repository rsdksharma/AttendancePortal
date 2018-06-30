package com.syars.attendance.service;

import java.util.Set;

import com.syars.attendance.constants.AttendanceConstants;
import com.syars.attendance.dao.UserDao;
import com.syars.attendance.vo.UserVO;

public class UserService {
	UserDao userDao = new UserDao();

	public String checkUserAuthorization(String userId, String password, Set<String> rolesSet) {

		//if user id, password and role defined matches then return passed
		UserVO user = userDao.getUserCredentials(userId);
		if(user == null) {
			return AttendanceConstants.INVALID_USER;
		}
		else {
			if (!password.equals(user.getPassword())) {
				return AttendanceConstants.INVALID_PASSWORD;
			}
			else {
				if(!rolesSet.contains(user.getRole())) {
					return AttendanceConstants.ROLE_NOT_ALLOWED;
				}
				else {
					return AttendanceConstants.PASSED;
				}
			}
		}
	}

	public void createUser(UserVO userVo) {
		userDao.createUser(userVo);
		
	}

}
