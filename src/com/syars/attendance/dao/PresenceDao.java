/**
Copyright: SYARS
2018

File Name: PresenceDao.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/

package com.syars.attendance.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoServerException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.WriteResult;
import com.syars.attendance.constants.DBCollectionAttributes;
import com.syars.attendance.exceptions.DatabaseException;
import com.syars.attendance.utils.DateFormatter;
import com.syars.attendance.utils.MongoDBUtils;
import com.syars.attendance.vo.PresenceVO;

public class PresenceDao {
	private DBCollection col = null;

	public int insertPresence(PresenceVO presenceVo) throws DatabaseException {
		int result = 0;
		
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.PRESENCE_COLLECTION);

			String formattedDate = DateFormatter.formatDate(new Date());
			DBObject query = BasicDBObjectBuilder.start().add(DBCollectionAttributes._ID, formattedDate).get();
			
			DBObject presenceDoc = new BasicDBObject();
			presenceDoc.put("$addToSet", new BasicDBObject(DBCollectionAttributes.MEMBER_ID, presenceVo.getMemberId()));
			WriteResult writeResult = col.update(query, presenceDoc);
			result = writeResult.getN();
			System.out.println(">>>>result_1:"+result);
			//if not updated then create a new list
			if (result == 0 ) {
				BasicDBObject presenceDocNew = mapToDBObject(presenceVo);
				WriteResult writeResultNew = col.insert(presenceDocNew);
				result = writeResultNew.getN();
				System.out.println(">>>>result_2:"+result);
			}
			System.out.println(">>>>result_final:"+result);
			return result;

		} catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		}
		finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
	}

	private BasicDBObject mapToDBObject(PresenceVO presenceVo) {
		BasicDBList list = new BasicDBList();
		list.add(presenceVo.getMemberId());
		
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
		BasicDBObject newDBObject = new BasicDBObject(
				DBCollectionAttributes._ID, DateFormatter.formatDate(new Date()))
				.append(DBCollectionAttributes.MEMBER_ID, list);

		return newDBObject;
	}

	public Map<String, Set<String>> retrievePresenceForAll() throws DatabaseException {
		Map<String, Set<String>> PresenceMap = new HashMap<String, Set<String>>();

		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.PRESENCE_COLLECTION);

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

	public Set<String> extractUniqueMembers() throws DatabaseException {
		Set<String> memberSet = new HashSet<String>();
		boolean publicMethodInvokationFlag = false;
		try {
			if(col == null) {
				col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.PRESENCE_COLLECTION);
				publicMethodInvokationFlag = true;
			}
			DBCursor cursor = col.find();
			while (cursor.hasNext()) {
				DBObject result = cursor.next();
				BasicDBList memberList = (BasicDBList)result.get(DBCollectionAttributes.MEMBER_ID);
				Set<String> members = new HashSet<String>();
				if(memberList != null) {
					for(Object obj: memberList) {
						members.add((String)obj);
					}
				}
				memberSet.addAll(members);
			}
		}catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		}
		if(publicMethodInvokationFlag) {
			// close resources
			MongoDBUtils.releaseResource();
		}
		System.out.println(">>>>UniqueMembersSet:"+memberSet);
		return memberSet;
	}

	public Set<String> extractPresenceDates(String memberId) throws DatabaseException {
		Set<String> dateSet = new HashSet<String>();
		boolean publicMethodInvokationFlag = false;
		try {
			if(col == null) {
				col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.PRESENCE_COLLECTION);
				publicMethodInvokationFlag = true;
			}
			BasicDBObject query = new BasicDBObject(DBCollectionAttributes.MEMBER_ID, memberId);
			DBCursor cursor = col.find(query);
			while (cursor.hasNext()) {
				DBObject result = cursor.next();
				/*Date formattedDate = DateFormatter
						.formatStringAsDate(result.get(DBCollectionAttributes._ID).toString());*/
				dateSet.add(result.get(DBCollectionAttributes._ID).toString());
			}
		}catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		}
		if(publicMethodInvokationFlag) {
			// close resources
			MongoDBUtils.releaseResource();
		}
		return dateSet;
	}
	
	public int getCount(String date) throws DatabaseException {
		int count = 0;
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.PRESENCE_COLLECTION);
			DBObject query = BasicDBObjectBuilder.start().add(DBCollectionAttributes._ID, date).get();
			DBCursor cursor = col.find(query);
			while (cursor.hasNext()) {
				DBObject result = cursor.next();
				List<String> members = (List<String>)result.get(DBCollectionAttributes.MEMBER_ID);
				System.out.println("Members"+members);
				count+=members.size();
			}
		}catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}

		return count;
	}

}
