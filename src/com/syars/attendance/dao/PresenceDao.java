package com.syars.attendance.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoServerException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.WriteResult;
import com.syars.attendance.exceptions.DatabaseException;
import com.syars.attendance.utils.MongoDBUtils;
import com.syars.attendance.vo.PresenceVO;

public class PresenceDao {
	private DBCollection col = null;

	public void insertPresence(PresenceVO presenceVo) throws DatabaseException {
		try {
			col = MongoDBUtils.getMongoDBCollection("AttendanceSheetCollection");

			// createDBObject. This object will be in JSON format.
			DBObject presenceDoc = mapToDBObject(presenceVo);

			// insert created feedback in MongoDB.
			WriteResult writeResult = col.insert(presenceDoc);
			if (writeResult.wasAcknowledged()) {
				System.out.println(">>>>insertion acknowledged");
			}

		} catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
	}

	private DBObject mapToDBObject(PresenceVO presenceVo) {
		BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

		docBuilder.append("MemberId", presenceVo.getMemberId());
		docBuilder.append("Date", new Date());

		return docBuilder.get();
	}
	
	public Set<Date> retrievePresence(String memberId) throws DatabaseException {
		Set<Date> dateSet;
		try {
			col = MongoDBUtils.getMongoDBCollection("AttendanceSheetCollection");
			// create query
			DBObject query = BasicDBObjectBuilder.start().add("MemberId", memberId).get();

			DBCursor cursor = col.find(query);
			dateSet = new HashSet<Date>();
			while (cursor.hasNext()) {
				DBObject result = cursor.next();
				dateSet.add((Date) result.get("Date"));
			}
		} catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
		return dateSet;
	}

	public Map<String, Set<Date>> retrievePresenceForAll() throws DatabaseException {
		Map<String, Set<Date>> PresenceMap = new HashMap<String, Set<Date>>();

		try {
			col = MongoDBUtils.getMongoDBCollection("AttendanceSheetCollection");

			// create set of members present
			Set<String> memberSet = extractUniqueMembers();
			for (String member : memberSet) {
				PresenceMap.put(member, extractPresenceDates(member));
			}

		} catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
		return PresenceMap;
	}

	private Set<String> extractUniqueMembers() {
		DBCursor cursor = col.find();
		Set<String> memberSet = new HashSet<String>();
		while (cursor.hasNext()) {
			DBObject result = cursor.next();
			memberSet.add(result.get("MemberId").toString());
		}
		return memberSet;
	}

	private Set<Date> extractPresenceDates(String member) {
		Set<Date> dateSet;
		DBObject query = BasicDBObjectBuilder.start().add("MemberId", member).get();

		DBCursor cursor = col.find(query);
		dateSet = new HashSet<Date>();
		while (cursor.hasNext()) {
			DBObject result = cursor.next();
			dateSet.add((Date) result.get("Date"));
		}
		return dateSet;
	}

}
