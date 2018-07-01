package com.syars.attendance.vo;

import java.util.Date;

public class PresenceVO {
	private String memberId;
	private Date date;

	
	public PresenceVO() {
		super();
	}

	public PresenceVO(String memberId, Date date) {
		super();
		this.memberId = memberId;
		this.date = date;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
