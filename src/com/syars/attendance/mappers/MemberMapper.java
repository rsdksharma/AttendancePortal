package com.syars.attendance.mappers;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.syars.attendance.constants.DBCollectionAttributes;
import com.syars.attendance.vo.MemberVO;

public class MemberMapper {

	/**
	 * Forward mapping for member - MemberVO to DBObject
	 * @param memberVo
	 * @param dbObject
	 * @return
	 */
	public DBObject doMap(MemberVO memberVo) {

		BasicDBObject memberDocument = new BasicDBObject();
		if (StringUtils.isNotEmpty(memberVo.getFullName())) {
			memberDocument.append(DBCollectionAttributes.FULL_NAME, memberVo.getFullName());
		}
		if (StringUtils.isNotEmpty(memberVo.getMobileNumber())) {
			memberDocument.append(DBCollectionAttributes.MOBILE_NUMBER, memberVo.getMobileNumber());
		}
		if (StringUtils.isNotEmpty(memberVo.getEmailId())) {
			memberDocument.append(DBCollectionAttributes.EMAIL_ID, memberVo.getEmailId());
		}
		if (memberVo.getDateOfBirth() != null) {
			memberDocument.append(DBCollectionAttributes.DATE_OF_BIRTH, memberVo.getDateOfBirth());
		}
		if (StringUtils.isNotEmpty(memberVo.getFirstName())) {
			memberDocument.append(DBCollectionAttributes.FIRST_NAME, memberVo.getFirstName());
		}
		if (StringUtils.isNotEmpty(memberVo.getLastName())) {
			memberDocument.append(DBCollectionAttributes.LAST_NAME, memberVo.getLastName());
		}
		if (StringUtils.isNotEmpty(memberVo.getFathersName())) {
			memberDocument.append(DBCollectionAttributes.FATHER_NAME, memberVo.getFathersName());
		}
		if (StringUtils.isNotEmpty(memberVo.getMothersName())) {
			memberDocument.append(DBCollectionAttributes.MOTHER_NAME, memberVo.getMothersName());
		}
		if (StringUtils.isNotEmpty(memberVo.getBranch())) {
			memberDocument.append(DBCollectionAttributes.BRANCH, memberVo.getBranch());
		}
		if (StringUtils.isNotEmpty(memberVo.getBranchID())) {
			memberDocument.append(DBCollectionAttributes.BRANCH_ID, memberVo.getBranchID());
		}
		if (StringUtils.isNotEmpty(memberVo.getMemberID())) {
			memberDocument.append(DBCollectionAttributes.MEMBER_ID, memberVo.getMemberID());
		}
		if (StringUtils.isNotEmpty(memberVo.getAadharID())) {
			memberDocument.append(DBCollectionAttributes.AADHAR_ID, memberVo.getAadharID());
		}
		if (StringUtils.isNotEmpty(memberVo.getGlobalUID())) {
			memberDocument.append(DBCollectionAttributes.GLOBAL_UID, memberVo.getGlobalUID());
		}
		if (StringUtils.isNotEmpty(memberVo.getFathersUID())) {
			memberDocument.append(DBCollectionAttributes.FATHER_UID, memberVo.getFathersUID());
		}
		if (StringUtils.isNotEmpty(memberVo.getMothersUID())) {
			memberDocument.append(DBCollectionAttributes.MOTHER_UID, memberVo.getMothersUID());
		}
		if (StringUtils.isNotEmpty(memberVo.getPictureId())) {
			memberDocument.append(DBCollectionAttributes.PICTURE_ID, memberVo.getPictureId());
		}
		if (StringUtils.isNotEmpty(memberVo.getSignatureId())) {
			memberDocument.append(DBCollectionAttributes.SIGNATURE_ID, memberVo.getSignatureId());
		}
		if (StringUtils.isNotEmpty(memberVo.getThumbId())) {
			memberDocument.append(DBCollectionAttributes.THUMB_ID, memberVo.getThumbId());
		}
		if (StringUtils.isNotEmpty(memberVo.getRegistrationDate())) {
			memberDocument.append(DBCollectionAttributes.REGISTRATION_DATE, memberVo.getRegistrationDate());
		}
		if (memberVo.getFirstInitiationDate() != null) {
			memberDocument.append(DBCollectionAttributes.FIRST_INITIATION_DT, memberVo.getFirstInitiationDate());
		}
		if (memberVo.getSecondInitiationDate() != null) {
			memberDocument.append(DBCollectionAttributes.SECOND_INITIATION_DT, memberVo.getSecondInitiationDate());
		}
		if (memberVo.isInitiatedFlag()) {
			memberDocument.append(DBCollectionAttributes.INITIATED_FLAG, memberVo.isInitiatedFlag());
		}
		if (memberVo.isJigyashuFlag()) {
			memberDocument.append(DBCollectionAttributes.JIGYASHU_FLAG, memberVo.isJigyashuFlag());
		}
		if (memberVo.isStudentFlag()) {
			memberDocument.append(DBCollectionAttributes.STUDENT_FLAG, memberVo.isStudentFlag());
		}
		if (memberVo.isUserAlsoFlag()) {
			memberDocument.append(DBCollectionAttributes.IS_USER, memberVo.isUserAlsoFlag());
		}
		if (StringUtils.isNotEmpty(memberVo.getQualification())) {
			memberDocument.append(DBCollectionAttributes.QUALIFICATION, memberVo.getQualification());
		}
		if (StringUtils.isNotEmpty(memberVo.getSpecialization())) {
			memberDocument.append(DBCollectionAttributes.SPECIALIZATION, memberVo.getSpecialization());
		}
		if (StringUtils.isNotEmpty(memberVo.getProfession())) {
			memberDocument.append(DBCollectionAttributes.PROFESSION, memberVo.getProfession());
		}

		return memberDocument;
	}

	/**
	 * Reverse mapping for member - DBObject to MemberVO
	 * @param DBObject
	 * @return MemberVO
	 */
	public MemberVO doMap(DBObject result) {
		MemberVO memberVo = new MemberVO();
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
