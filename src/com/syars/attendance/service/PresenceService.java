package com.syars.attendance.service;

import java.util.Date;
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
		int result = 1;
		try {
			presenceDao.insertPresence(presenceVo);
		} catch (DatabaseException e) {
			result = 503;
		}
		return result;
	}

	public Set<Date> retrievePresenceByMemberId(String memberId) {
		try {
			return presenceDao.retrievePresence(memberId);
		} catch (DatabaseException e) {
			System.out.println("DatabaseException occured:" + e.getMessage());
			return null;
		}
	}

	public Map<String, Set<Date>> retrievePresenceForAll() {
		Map<String, Set<Date>> retVal = new HashMap<String, Set<Date>>();
		try {
			return presenceDao.retrievePresenceForAll();
		} catch (DatabaseException e) {
			retVal.put(AttendanceConstants.DB_EXCEPTION, null);
		}
		return retVal;
	}

}
