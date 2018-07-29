package com.syars.attendance.mappers;

import org.apache.commons.lang3.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.syars.attendance.constants.DBCollectionAttributes;
import com.syars.attendance.vo.UserVO;

public class UserMapper {

	/**
	 * Reverse mapping for user credentials, DBObject-->UserVO.
	 * @param DBObject
	 * @return UserVO
	 */
	public UserVO mapCredentials(DBObject result) {
		UserVO userVo = new UserVO();
		if (result.get(DBCollectionAttributes.PASSWORD) != null) {
			userVo.setPassword(result.get(DBCollectionAttributes.PASSWORD).toString());
		}
		if (result.get(DBCollectionAttributes.USER_ROLE) != null) {
			userVo.setUserRole(result.get(DBCollectionAttributes.USER_ROLE).toString());
		}

		return userVo;
	}

	/**
	 * Reverse mapping for UserVO, DBObject-->UserVO.
	 * @param DBObject
	 * @return UserVO
	 */
	public UserVO doMap(DBObject result) {
		UserVO userVo = new UserVO();
		if (result.get(DBCollectionAttributes.MEMBER_ID) != null) {
			userVo.setMemberId(result.get(DBCollectionAttributes.MEMBER_ID).toString());
		}
		if (result.get(DBCollectionAttributes.USER_ID) != null) {
			userVo.setUserId(result.get(DBCollectionAttributes.USER_ID).toString());
		}
		if (result.get(DBCollectionAttributes.IS_USER_ID_CUSTOMIZED) != null) {
			boolean isIdCustomized = (boolean) result.get(DBCollectionAttributes.IS_USER_ID_CUSTOMIZED);
			userVo.setUserIdCustomized(isIdCustomized);
			if (isIdCustomized && result.get(DBCollectionAttributes.CUSTOMIZED_USER_ID) != null) {
				userVo.setCustomizedUserId(result.get(DBCollectionAttributes.CUSTOMIZED_USER_ID).toString());
			}
		}
		if (result.get(DBCollectionAttributes.USER_ROLE) != null) {
			userVo.setUserRole(result.get(DBCollectionAttributes.USER_ROLE).toString());
		}
		if (result.get(DBCollectionAttributes.PASSWORD) != null) {
			 // userVo.setPassword(result.get(DBCollectionAttributes.PASSWORD).toString());
		}

		return userVo;
	}

	/**
	 * Forward mapping for UserVO, UserVO-->DBObject
	 * @param UserVO
	 * @return DBObject
	 */
	public BasicDBObject doMap(UserVO userVo) {
		BasicDBObject userDocument = new BasicDBObject();

		if (StringUtils.isNotBlank(userVo.getMemberId())) {
			userDocument.append(DBCollectionAttributes.MEMBER_ID, userVo.getMemberId());
		}
		if (StringUtils.isNotBlank(userVo.getPassword())) {
			userDocument.append(DBCollectionAttributes.PASSWORD, userVo.getPassword());
		}
		if (StringUtils.isNotBlank(userVo.getUserRole())) {
			userDocument.append(DBCollectionAttributes.USER_ROLE, userVo.getUserRole());
		}
		if (StringUtils.isNotBlank(userVo.getCustomizedUserId())) {
			userDocument.append(DBCollectionAttributes.IS_USER_ID_CUSTOMIZED, true);
			userDocument.append(DBCollectionAttributes.CUSTOMIZED_USER_ID, userVo.getCustomizedUserId());
		}
		return userDocument;
	}
}
