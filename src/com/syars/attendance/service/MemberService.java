package com.syars.attendance.service;

import com.syars.attendance.dao.MemberDao;
import com.syars.attendance.vo.MemberVO;

public class MemberService {
	MemberDao memberDao = new MemberDao();

	public MemberVO getMember(String memberId) {
		return memberDao.getMember(memberId);
	}

	public void createMember(MemberVO memberVo) {
		memberDao.createMember(memberVo);
	}

}
