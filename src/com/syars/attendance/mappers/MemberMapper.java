package com.syars.attendance.mappers;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.mongodb.DBObject;
import com.syars.attendance.constants.DBCollectionAttributes;
import com.syars.attendance.vo.MemberVO;

public class MemberMapper {

	// Forward mapping - MemberVO to DBObject
	public DBObject doMap(MemberVO memberVo, DBObject dbObject) {

		if (StringUtils.isNotEmpty(memberVo.getFullName())) {
			dbObject.put(DBCollectionAttributes.FULL_NAME, memberVo.getFullName());
		}
		if (StringUtils.isNotEmpty(memberVo.getMobileNumber())) {
			dbObject.put(DBCollectionAttributes.MOBILE_NUMBER, memberVo.getMobileNumber());
		}
		if (StringUtils.isNotEmpty(memberVo.getEmailId())) {
			dbObject.put(DBCollectionAttributes.EMAIL_ID, memberVo.getEmailId());
		}
		if (memberVo.getDateOfBirth() != null) {
			dbObject.put(DBCollectionAttributes.DATE_OF_BIRTH, memberVo.getDateOfBirth());
		}
		if (StringUtils.isNotEmpty(memberVo.getFirstName())) {
			dbObject.put(DBCollectionAttributes.FIRST_NAME, memberVo.getFirstName());
		}
		if (StringUtils.isNotEmpty(memberVo.getLastName())) {
			dbObject.put(DBCollectionAttributes.LAST_NAME, memberVo.getLastName());
		}
		if (StringUtils.isNotEmpty(memberVo.getFathersName())) {
			dbObject.put(DBCollectionAttributes.FATHER_NAME, memberVo.getFathersName());
		}
		if (StringUtils.isNotEmpty(memberVo.getMothersName())) {
			dbObject.put(DBCollectionAttributes.MOTHER_NAME, memberVo.getMothersName());
		}
		if (StringUtils.isNotEmpty(memberVo.getBranch())) {
			dbObject.put(DBCollectionAttributes.BRANCH, memberVo.getBranch());
		}
		if (StringUtils.isNotEmpty(memberVo.getBranchID())) {
			dbObject.put(DBCollectionAttributes.BRANCH_ID, memberVo.getBranchID());
		}
		if (StringUtils.isNotEmpty(memberVo.getMemberID())) {
			dbObject.put(DBCollectionAttributes.MEMBER_ID, memberVo.getMemberID());
		}
		if (StringUtils.isNotEmpty(memberVo.getAadharID())) {
			dbObject.put(DBCollectionAttributes.AADHAR_ID, memberVo.getAadharID());
		}
		if (StringUtils.isNotEmpty(memberVo.getGlobalUID())) {
			dbObject.put(DBCollectionAttributes.GLOBAL_UID, memberVo.getGlobalUID());
		}
		if (StringUtils.isNotEmpty(memberVo.getFathersUID())) {
			dbObject.put(DBCollectionAttributes.FATHER_UID, memberVo.getFathersUID());
		}
		if (StringUtils.isNotEmpty(memberVo.getMothersUID())) {
			dbObject.put(DBCollectionAttributes.MOTHER_UID, memberVo.getMothersUID());
		}
		if (StringUtils.isNotEmpty(memberVo.getPictureId())) {
			dbObject.put(DBCollectionAttributes.PICTURE_ID, memberVo.getPictureId());
		}
		if (StringUtils.isNotEmpty(memberVo.getSignatureId())) {
			dbObject.put(DBCollectionAttributes.SIGNATURE_ID, memberVo.getSignatureId());
		}
		if (StringUtils.isNotEmpty(memberVo.getThumbId())) {
			dbObject.put(DBCollectionAttributes.THUMB_ID, memberVo.getThumbId());
		}
		if (StringUtils.isNotEmpty(memberVo.getRegistrationDate())) {
			dbObject.put(DBCollectionAttributes.REGISTRATION_DATE, memberVo.getRegistrationDate());
		}
		if (memberVo.getFirstInitiationDate() != null) {
			dbObject.put(DBCollectionAttributes.FIRST_INITIATION_DT, memberVo.getFirstInitiationDate());
		}
		if (memberVo.getSecondInitiationDate() != null) {
			dbObject.put(DBCollectionAttributes.SECOND_INITIATION_DT, memberVo.getSecondInitiationDate());
		}
		if (memberVo.isInitiatedFlag()) {
			dbObject.put(DBCollectionAttributes.INITIATED_FLAG, memberVo.isInitiatedFlag());
		}
		if (memberVo.isJigyashuFlag()) {
			dbObject.put(DBCollectionAttributes.JIGYASHU_FLAG, memberVo.isJigyashuFlag());
		}
		if (memberVo.isStudentFlag()) {
			dbObject.put(DBCollectionAttributes.STUDENT_FLAG, memberVo.isStudentFlag());
		}
		if (memberVo.isUserAlsoFlag()) {
			dbObject.put(DBCollectionAttributes.IS_USER, memberVo.isUserAlsoFlag());
		}
		if (StringUtils.isNotEmpty(memberVo.getQualification())) {
			dbObject.put(DBCollectionAttributes.QUALIFICATION, memberVo.getQualification());
		}
		if (StringUtils.isNotEmpty(memberVo.getSpecialization())) {
			dbObject.put(DBCollectionAttributes.SPECIALIZATION, memberVo.getSpecialization());
		}
		if (StringUtils.isNotEmpty(memberVo.getProfession())) {
			dbObject.put(DBCollectionAttributes.PROFESSION, memberVo.getProfession());
		}

		return dbObject;
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
		if (result.get(DBCollectionAttributes.DATE_OF_BIRTH) != null) {
			memberVo.setDateOfBirth((Date) result.get(DBCollectionAttributes.DATE_OF_BIRTH));
		}
		if (result.get(DBCollectionAttributes.FIRST_NAME) != null) {
			memberVo.setFirstName(result.get(DBCollectionAttributes.FIRST_NAME).toString());
		}
		if (result.get(DBCollectionAttributes.LAST_NAME) != null) {
			memberVo.setLastName(result.get(DBCollectionAttributes.LAST_NAME).toString());
		}
		if (result.get(DBCollectionAttributes.FATHER_NAME) != null) {
			memberVo.setFathersName(result.get(DBCollectionAttributes.FATHER_NAME).toString());
		}
		if (result.get(DBCollectionAttributes.MOTHER_NAME) != null) {
			memberVo.setMothersName(result.get(DBCollectionAttributes.MOTHER_NAME).toString());
		}
		if (result.get(DBCollectionAttributes.FATHER_UID) != null) {
			memberVo.setFathersUID(result.get(DBCollectionAttributes.FATHER_UID).toString());
		}
		if (result.get(DBCollectionAttributes.MOTHER_UID) != null) {
			memberVo.setMothersUID(result.get(DBCollectionAttributes.MOTHER_UID).toString());
		}
		if (result.get(DBCollectionAttributes.BRANCH) != null) {
			memberVo.setBranch(result.get(DBCollectionAttributes.BRANCH).toString());
		}
		if (result.get(DBCollectionAttributes.BRANCH_ID) != null) {
			memberVo.setBranchID(result.get(DBCollectionAttributes.BRANCH_ID).toString());
		}
		if (result.get(DBCollectionAttributes.AADHAR_ID) != null) {
			memberVo.setAadharID(result.get(DBCollectionAttributes.AADHAR_ID).toString());
		}
		if (result.get(DBCollectionAttributes.GLOBAL_UID) != null) {
			memberVo.setGlobalUID(result.get(DBCollectionAttributes.GLOBAL_UID).toString());
		}
		if (result.get(DBCollectionAttributes.PICTURE_ID) != null) {
			memberVo.setPictureId(result.get(DBCollectionAttributes.PICTURE_ID).toString());
		}
		if (result.get(DBCollectionAttributes.SIGNATURE_ID) != null) {
			memberVo.setSignatureId(result.get(DBCollectionAttributes.SIGNATURE_ID).toString());
		}
		if (result.get(DBCollectionAttributes.THUMB_ID) != null) {
			memberVo.setThumbId(result.get(DBCollectionAttributes.THUMB_ID).toString());
		}
		if (result.get(DBCollectionAttributes.REGISTRATION_DATE) != null) {
			memberVo.setRegistrationDate(result.get(DBCollectionAttributes.REGISTRATION_DATE).toString());
		}
		if (result.get(DBCollectionAttributes.FIRST_INITIATION_DT) != null) {
			memberVo.setFirstInitiationDate((Date) result.get(DBCollectionAttributes.FIRST_INITIATION_DT));
		}
		if (result.get(DBCollectionAttributes.SECOND_INITIATION_DT) != null) {
			memberVo.setSecondInitiationDate((Date) result.get(DBCollectionAttributes.SECOND_INITIATION_DT));
		}
		if (result.get(DBCollectionAttributes.QUALIFICATION) != null) {
			memberVo.setQualification(result.get(DBCollectionAttributes.QUALIFICATION).toString());
		}
		if (result.get(DBCollectionAttributes.PROFESSION) != null) {
			memberVo.setProfession(result.get(DBCollectionAttributes.PROFESSION).toString());
		}
		if (result.get(DBCollectionAttributes.SPECIALIZATION) != null) {
			memberVo.setSpecialization(result.get(DBCollectionAttributes.SPECIALIZATION).toString());
		}
		if (result.get(DBCollectionAttributes.INITIATED_FLAG) != null) {
			memberVo.setInitiatedFlag((boolean) result.get(DBCollectionAttributes.INITIATED_FLAG));
		}
		if (result.get(DBCollectionAttributes.JIGYASHU_FLAG) != null) {
			memberVo.setJigyashuFlag((boolean) result.get(DBCollectionAttributes.JIGYASHU_FLAG));
		}
		if (result.get(DBCollectionAttributes.STUDENT_FLAG) != null) {
			memberVo.setStudentFlag((boolean) result.get(DBCollectionAttributes.STUDENT_FLAG));
		}
		return memberVo;
	}

}
