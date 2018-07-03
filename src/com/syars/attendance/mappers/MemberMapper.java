package com.syars.attendance.mappers;

import org.apache.commons.lang3.StringUtils;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.syars.attendance.constants.DBCollectionAttributes;
import com.syars.attendance.vo.MemberVO;

public class MemberMapper {

	// Forward mapping - MemberVO to DBObject
	public DBObject doMap(MemberVO memberVo) {
		BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

		if(StringUtils.isNotEmpty(memberVo.getFullName())){
			docBuilder.append(DBCollectionAttributes.FULL_NAME, memberVo.getFullName());
		}
		if(StringUtils.isNotEmpty(memberVo.getMobileNumber())){
			docBuilder.append(DBCollectionAttributes.MOBILE_NUMBER, memberVo.getMobileNumber());
		}
		if(StringUtils.isNotEmpty(memberVo.getEmailId())) {
			docBuilder.append(DBCollectionAttributes.EMAIL_ID, memberVo.getEmailId());
		}

		return docBuilder.get();
	}

	// Reverse mapping - DBObject to MemberVO
	public MemberVO doMap(DBObject result, MemberVO memberVo) {
		if (result.get(DBCollectionAttributes.MEMBER_ID) != null) {
			memberVo.setMemberID(result.get(DBCollectionAttributes.MEMBER_ID).toString());
		}
		if (result.get(DBCollectionAttributes.FULL_NAME) != null) {
			memberVo.setFullName(result.get(DBCollectionAttributes.FULL_NAME).toString());
		}
		if (result.get(DBCollectionAttributes.MOBILE_NUMBER) != null) {
			memberVo.setMobileNumber(result.get(DBCollectionAttributes.MOBILE_NUMBER).toString());
		}
		if (result.get(DBCollectionAttributes.EMAIL_ID) != null) {
			memberVo.setEmailId(result.get(DBCollectionAttributes.EMAIL_ID).toString());
		}
		if (result.get(DBCollectionAttributes.IS_USER) != null) {
			memberVo.setUserAlsoFlag((boolean) result.get(DBCollectionAttributes.IS_USER));
		}
		// TODO map other rest attributes
		// if(result.get(DBCollectionAttributes.) != null) {
		//
		// }
		return memberVo;
	}
}
