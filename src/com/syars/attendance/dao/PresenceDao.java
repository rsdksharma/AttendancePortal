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

import java.util.ArrayList;
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
import com.syars.attendance.vo.MemberVO;
import com.syars.attendance.vo.PresenceVO;
import com.syars.attendance.vo.ResponseVO;
import com.ulok.inf.logger.MessageLogger;

public class PresenceDao {
	private DBCollection col = null;

	public int insertPresence(PresenceVO presenceVo) throws DatabaseException {
		final String methodName = "insertPresence";
		int result = 0;
		
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.PRESENCE_COLLECTION);

			String formattedDate = DateFormatter.formatDate(new Date());
			DBObject query = BasicDBObjectBuilder.start().add(DBCollectionAttributes._ID, formattedDate).get();
			
			DBObject presenceDoc = new BasicDBObject();
			presenceDoc.put("$addToSet", new BasicDBObject(DBCollectionAttributes.MEMBER_ID, presenceVo.getMemberId()));
			WriteResult writeResult = col.update(query, presenceDoc);
			result = writeResult.getN();
			
			//if not updated then create a new list
			if (!writeResult.isUpdateOfExisting() ) {
				BasicDBObject presenceDocNew = mapToDBObject(presenceVo);
				writeResult = col.insert(presenceDocNew);
				result++;
			}
			MessageLogger.logInfo(this, methodName, presenceVo.getMemberId(), "result of presence insertion:"+writeResult);
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

	public List<ResponseVO> getAllPresenceResponse() throws DatabaseException{
		List<ResponseVO> responseList = new ArrayList<ResponseVO>();
		try {
			MemberDao memberDao = new MemberDao();
			Map<String, MemberVO> memberMap = memberDao.getAllMembers();
			col = null;
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.PRESENCE_COLLECTION);

			// create set of members present
			Set<String> memberSet = extractUniqueMembers();
			for (String member : memberSet) {
				ResponseVO responseVO = new ResponseVO();
				responseVO.setMemberVo(memberMap.get(member));
				responseVO.setAttendanceSet(extractPresenceDates(member));
				
				responseList.add(responseVO);
			}

		} catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
		
		return responseList;
	}
	public Map<MemberVO, Set<String>> retrievePresenceForAll() throws DatabaseException {
		
		Map<MemberVO, Set<String>> retVal = new HashMap<MemberVO, Set<String>>();

		try {
			MemberDao memberDao = new MemberDao();
			Map<String, MemberVO> memberMap = memberDao.getAllMembers();
			col = null;
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.PRESENCE_COLLECTION);

			// create set of members present
			Set<String> memberSet = extractUniqueMembers();
			for (String member : memberSet) {
				retVal.put(memberMap.get(member), extractPresenceDates(member));
			}

		} catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
		return retVal;
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
	
	public List<String> retrieveCountByDate(String date) throws DatabaseException{
		
		List<String> members = new ArrayList<String>();
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.PRESENCE_COLLECTION);
			DBObject query = BasicDBObjectBuilder.start().add(DBCollectionAttributes._ID, date).get();
			DBCursor cursor = col.find(query);
			while (cursor.hasNext()) {
				DBObject result = cursor.next();
				members = (List<String>)result.get(DBCollectionAttributes.MEMBER_ID);
				System.out.println("Members"+members);
			}
		}catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
		return members;
	}
	
public List<ResponseVO> retrieveAttendanceByDate(String date) throws DatabaseException{
		
		List<ResponseVO> responseList = new ArrayList<ResponseVO>();
		MemberDao memberDao = new MemberDao();
		List<String> members = null;
		try {
			Map<String, MemberVO> memberMap = memberDao.getAllMembers();
			col = null;
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.PRESENCE_COLLECTION);
			DBObject query = BasicDBObjectBuilder.start().add(DBCollectionAttributes._ID, date).get();
			DBCursor cursor = col.find(query);
			while (cursor.hasNext()) {
				DBObject result = cursor.next();
				members = (List<String>)result.get(DBCollectionAttributes.MEMBER_ID);
			}
			if(members != null) {
				for (String member : members) {
					ResponseVO responseVO = new ResponseVO();
					responseVO.setMemberVo(memberMap.get(member));
					//responseVO.setAttendanceSet(extractPresenceDates(member));
					
					responseList.add(responseVO);
				}
			}
			
		}catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
		return responseList;
	}

}
