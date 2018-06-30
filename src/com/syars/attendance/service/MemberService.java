package com.syars.attendance.service;

import java.util.Map;

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

	public Map<String, MemberVO> getAllMembers() {
		return memberDao.getAllMembers();
	}

}
