/**
Copyright: SYARS
2018

File Name: MemberService.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/

package com.syars.attendance.service;

import java.util.Map;

import com.syars.attendance.dao.MemberDao;
import com.syars.attendance.exceptions.DatabaseException;
import com.syars.attendance.vo.MemberVO;

public class MemberService {
	MemberDao memberDao = new MemberDao();

	public MemberVO getMember(String memberId) {
		try {
			return memberDao.getMember(memberId);
		} catch (DatabaseException e) {
			System.out.println("DatabaseException occured:"+e.getMessage());
			return null;
		}
	}

	public String createMember(MemberVO memberVo) {
		String createdMemberId = null;
		try {
			createdMemberId = memberDao.createMember(memberVo);
		} catch (DatabaseException e) {
			createdMemberId = null;
		}
		return createdMemberId;
	}

	public Map<String, MemberVO> getAllMembers() {
		try {
			return memberDao.getAllMembers();
		} catch (DatabaseException e) {
			System.out.println("DatabaseException occured:"+e.getMessage());
			return null;
		}
	}
	
	public int updateMember(MemberVO memberVo) {
		int result = 0;
		try {
			result = memberDao.updateMember(memberVo);
		} catch (DatabaseException e) {
			//some logging
		}
		return result;
	}

	public int deleteMember(String memberId) {
		int result = 0;
		try {
			result = memberDao.deleteMember(memberId);
		} catch (DatabaseException e) {
		}
		return result;
	}

}
