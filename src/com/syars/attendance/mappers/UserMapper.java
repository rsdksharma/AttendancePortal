package com.syars.attendance.mappers;

import org.apache.commons.lang3.StringUtils;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.syars.attendance.constants.DBCollectionAttributes;
import com.syars.attendance.vo.UserVO;

public class UserMapper {

	public UserVO mapCredentials(DBObject result, UserVO userVO) {
		if (result.get(DBCollectionAttributes.PASSWORD) != null) {
			userVO.setPassword(result.get(DBCollectionAttributes.PASSWORD).toString());
		}
		if (result.get(DBCollectionAttributes.USER_ROLE) != null) {
			userVO.setUserRole(result.get(DBCollectionAttributes.USER_ROLE).toString());
		}

		return userVO;
	}

	public UserVO doMap(DBObject result, UserVO userVO) {
		if (result.get(DBCollectionAttributes.MEMBER_ID) != null) {
			userVO.setMemberId(result.get(DBCollectionAttributes.MEMBER_ID).toString());
		}
		if (result.get(DBCollectionAttributes.USER_ID) != null) {
			userVO.setUserId(result.get(DBCollectionAttributes.USER_ID).toString());
		}
		if (result.get(DBCollectionAttributes.IS_USER_ID_CUSTOMIZED) != null) {
			boolean isIdCustomized = (boolean) result.get(DBCollectionAttributes.IS_USER_ID_CUSTOMIZED);
			userVO.setUserIdCustomized(isIdCustomized);
			if (isIdCustomized && result.get(DBCollectionAttributes.CUSTOMIZED_USER_ID) != null) {
				userVO.setCustomizedUserId(result.get(DBCollectionAttributes.CUSTOMIZED_USER_ID).toString());
			}
		}
		if (result.get(DBCollectionAttributes.USER_ROLE) != null) {
			userVO.setUserRole(result.get(DBCollectionAttributes.USER_ROLE).toString());
		}
		if (result.get(DBCollectionAttributes.PASSWORD) != null) {
			// userVO.setPassword(result.get(DBCollectionAttributes.PASSWORD).toString());
		}

		return userVO;
	}

	public DBObject doMap(UserVO userVo) {
		BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

		if (StringUtils.isNotBlank(userVo.getMemberId())) {
			docBuilder.append(DBCollectionAttributes.MEMBER_ID, userVo.getMemberId());
		}
		if (StringUtils.isNotBlank(userVo.getPassword())) {
			docBuilder.append(DBCollectionAttributes.PASSWORD, userVo.getPassword());
		}
		if (StringUtils.isNotBlank(userVo.getUserRole())) {
			docBuilder.append(DBCollectionAttributes.USER_ROLE, userVo.getUserRole());
		}
		if (StringUtils.isNotBlank(userVo.getCustomizedUserId())) {
			docBuilder.append(DBCollectionAttributes.IS_USER_ID_CUSTOMIZED, true);
			docBuilder.append(DBCollectionAttributes.CUSTOMIZED_USER_ID, userVo.getCustomizedUserId());
		}
		return docBuilder.get();
	}

}
