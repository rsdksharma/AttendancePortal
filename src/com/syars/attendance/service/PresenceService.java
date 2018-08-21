/**
Copyright: SYARS
2018

File Name: PresenceService.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/

package com.syars.attendance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.syars.attendance.dao.MemberDao;
import com.syars.attendance.dao.PresenceDao;
import com.syars.attendance.dao.UserDao;
import com.syars.attendance.exceptions.DatabaseException;
import com.syars.attendance.vo.PresenceVO;
import com.syars.attendance.vo.ResponseVO;

public class PresenceService {
	PresenceDao presenceDao = new PresenceDao();
	UserDao userDao = new UserDao();
	MemberDao memberDao = new MemberDao();

	public int insertPresence(PresenceVO presenceVo) {
		int result = 0;
		try {
			boolean isMemberRegistered = memberDao.isMemberRegistered(presenceVo.getMemberId(), false);
			if(isMemberRegistered) {
				result = presenceDao.insertPresence(presenceVo);
			}
			else {
				result = -1;
			}
		} catch (DatabaseException e) {
			result = 503;
		}
		return result;
	}

	public Set<String> retrievePresenceByMemberId(String memberId) {
		try {
			return presenceDao.extractPresenceDates(memberId);
		} catch (DatabaseException e) {
			System.out.println("DatabaseException occured:" + e.getMessage());
			return null;
		}
	}

	public List<ResponseVO> retrievePresenceForAll() {
		List<ResponseVO> retVal = new ArrayList<ResponseVO>();
		try {
			retVal = presenceDao.getAllPresenceResponse();
		} catch (DatabaseException e) {
			//retVal.put(AttendanceConstants.DB_EXCEPTION, null);
		}
		return retVal;
	}

	public List<ResponseVO> retrieveAttendanceByDate(String date){
		List<ResponseVO> retVal = null;
		try {
			retVal = presenceDao.retrieveAttendanceByDate(date);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retVal;
	}
	public int getCount(String date) {
		int count = 0;
		try {
			count = presenceDao.retrieveCountByDate(date).size();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		return count;
	}

}
