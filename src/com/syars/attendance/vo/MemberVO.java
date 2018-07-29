package com.syars.attendance.vo;

import java.util.Date;

import com.syars.attendance.enums.MemberEnum;

public class MemberVO {

	// @NotNull
	private String fullName;
	// @NotNull
	private Date dateOfBirth;
	// @NotNull
	private String mobileNumber;

	private String firstName;
	private String lastName;
	private String fathersName;
	private String mothersName;

	private String branch;
	private String emailId;
	private String memberID; // auto generated
	private String aadharID;
	private String branchID; // auto generated
	private String globalUID;
	private String fathersUID;
	private String mothersUID;

	private String pictureId; // auto generate and save
	private String signatureId; // auto generate and save
	private String thumbId; // auto generate and save

	private String registrationDate;
	private Date firstInitiationDate;
	private Date secondInitiationDate;

	private MemberEnum memberType;
	private boolean initiatedFlag;
	private boolean jigyashuFlag;
	private boolean studentFlag;
	private boolean userAlsoFlag;

	private String qualification;
	private String profession;
	private String specialization;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFathersName() {
		return fathersName;
	}

	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	public String getMothersName() {
		return mothersName;
	}

	public void setMothersName(String mothersName) {
		this.mothersName = mothersName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getAadharID() {
		return aadharID;
	}

	public void setAadharID(String aadharID) {
		this.aadharID = aadharID;
	}

	public String getBranchID() {
		return branchID;
	}

	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}

	public String getGlobalUID() {
		return globalUID;
	}

	public void setGlobalUID(String globalUID) {
		this.globalUID = globalUID;
	}

	public String getFathersUID() {
		return fathersUID;
	}

	public void setFathersUID(String fathersUID) {
		this.fathersUID = fathersUID;
	}

	public String getMothersUID() {
		return mothersUID;
	}

	public void setMothersUID(String mothersUID) {
		this.mothersUID = mothersUID;
	}

	public String getPictureId() {
		return pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	public String getSignatureId() {
		return signatureId;
	}

	public void setSignatureId(String signatureId) {
		this.signatureId = signatureId;
	}

	public String getThumbId() {
		return thumbId;
	}

	public void setThumbId(String thumbId) {
		this.thumbId = thumbId;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getFirstInitiationDate() {
		return firstInitiationDate;
	}

	public void setFirstInitiationDate(Date firstInitiationDate) {
		this.firstInitiationDate = firstInitiationDate;
	}

	public Date getSecondInitiationDate() {
		return secondInitiationDate;
	}

	public void setSecondInitiationDate(Date secondInitiationDate) {
		this.secondInitiationDate = secondInitiationDate;
	}

	public MemberEnum getMemberType() {
		return memberType;
	}

	public void setMemberType(MemberEnum memberType) {
		this.memberType = memberType;
	}

	public boolean isInitiatedFlag() {
		return initiatedFlag;
	}

	public void setInitiatedFlag(boolean isInitiated) {
		this.initiatedFlag = isInitiated;
	}

	public boolean isJigyashuFlag() {
		return jigyashuFlag;
	}

	public void setJigyashuFlag(boolean isJigyashu) {
		this.jigyashuFlag = isJigyashu;
	}

	public boolean isStudentFlag() {
		return studentFlag;
	}

	public void setStudentFlag(boolean isStudent) {
		this.studentFlag = isStudent;
	}

	public boolean isUserAlsoFlag() {
		return userAlsoFlag;
	}

	public void setUserAlsoFlag(boolean userAlso) {
		this.userAlsoFlag = userAlso;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

}
