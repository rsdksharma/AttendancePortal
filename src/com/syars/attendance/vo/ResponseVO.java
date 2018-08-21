package com.syars.attendance.vo;

import java.util.Set;

public class ResponseVO {
	private MemberVO memberVo;
	private Set<String> attendanceSet;

	public MemberVO getMemberVo() {
		return memberVo;
	}

	public void setMemberVo(MemberVO memberVo) {
		this.memberVo = memberVo;
	}

	public Set<String> getAttendanceSet() {
		return attendanceSet;
	}

	public void setAttendanceSet(Set<String> attendanceSet) {
		this.attendanceSet = attendanceSet;
	}

}
