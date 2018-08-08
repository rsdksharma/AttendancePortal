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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.syars.attendance.constants.AttendanceConstants;
import com.syars.attendance.dao.PresenceDao;
import com.syars.attendance.exceptions.DatabaseException;
import com.syars.attendance.vo.PresenceVO;

public class PresenceService {
	PresenceDao presenceDao = new PresenceDao();

	public int insertPresence(PresenceVO presenceVo) {
		int result = 0;
		try {
			result = presenceDao.insertPresence(presenceVo);
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

	public Map<String, Set<String>> retrievePresenceForAll() {
		Map<String, Set<String>> retVal = new HashMap<String, Set<String>>();
		try {
			return presenceDao.retrievePresenceForAll();
		} catch (DatabaseException e) {
			retVal.put(AttendanceConstants.DB_EXCEPTION, null);
		}
		return retVal;
	}

	public int getCount(String date) {
		int count = 0;
		try {
			count = presenceDao.getCount(date);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		return count;
	}

}
