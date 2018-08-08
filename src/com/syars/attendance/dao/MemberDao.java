/**
Copyright: SYARS
2018

File Name: MemberDao.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/

package com.syars.attendance.dao;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoServerException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.WriteResult;
import com.syars.attendance.constants.AttendanceConstants;
import com.syars.attendance.constants.DBCollectionAttributes;
import com.syars.attendance.constants.DBConstants;
import com.syars.attendance.exceptions.DatabaseException;
import com.syars.attendance.mappers.MemberMapper;
import com.syars.attendance.utils.MongoDBUtils;
import com.syars.attendance.vo.MemberVO;

public class MemberDao {
	private DBCollection col = null;
	MemberMapper mapper = new MemberMapper();

	public String createMember(MemberVO memberVo) throws DatabaseException {
		String createdMemberId = null;
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.MEMBER_COLLECTION);

			// createDBObject. This object will be in JSON format.
			DBObject memberDoc = mapper.doMap(memberVo);

			String memberId = createUniqueMemberId(memberVo);
			memberDoc.put(DBCollectionAttributes.MEMBER_ID, memberId);
			memberDoc.put("_id", memberId);

			// insert created memberDoc in MongoDB.
			WriteResult writeResult = col.insert(memberDoc);
			if (writeResult.wasAcknowledged()) {
				createdMemberId = memberId;
			}

		} catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
		return createdMemberId;
	}

	private String createUniqueMemberId(MemberVO memberVo) {
		/*String uniqueID = UUID.randomUUID().toString();
		StringTokenizer tokenizer = new StringTokenizer(uniqueID, "-");
		StringBuilder builder = new StringBuilder();
		builder.append(AttendanceConstants.MEMBER_PREFIX);
		builder.append(AttendanceConstants.BRANCH_CODE_);
		String memberId = builder.append(tokenizer.nextToken()).toString();
		System.out.println(">>>>memberId:" + memberId);*/

		return AttendanceConstants.MEMBER_PREFIX + AttendanceConstants.BRANCH_CODE_
				+ MongoDBUtils.getNextValue(DBCollectionAttributes.MEMBER_SEQUENCE);
	}

	public Map<String, MemberVO> getAllMembers() throws DatabaseException {
		Map<String, MemberVO> memberMap = new HashMap<String, MemberVO>();
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.MEMBER_COLLECTION);
			DBCursor cursor = col.find();
			while (cursor.hasNext()) {
				DBObject result = cursor.next();
				memberMap.put(result.get(DBCollectionAttributes.MEMBER_ID).toString(),
						mapper.doMap(result));
			}
		} catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
		return memberMap;
	}

	public MemberVO getMember(String memberId) throws DatabaseException {
		return getAllMembers().get(memberId);
	}

	public int getMemberCount() throws DatabaseException {
		int count = 0;
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.MEMBER_COLLECTION);
			if (col.getStats() != null) {
				count = col.getStats().getInt(DBConstants.RECORD_COUNT);
			}
		} catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}

		return count;
	}

	public int updateMember(MemberVO memberVo) throws DatabaseException {
		int updateResult = 0;
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.MEMBER_COLLECTION);

			// create query
			DBObject query = BasicDBObjectBuilder.start()
					.add(DBCollectionAttributes.MEMBER_ID, (memberVo.getMemberID())).get();
			
			// append $set operator to update passed fields
			DBObject userDoc = new BasicDBObject();
			userDoc.put("$set", mapper.doMap(memberVo));
			
			WriteResult result = col.update(query, userDoc);
			if(result.isUpdateOfExisting()) {
				updateResult = 1;
			}
		} catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
		return updateResult;
	}

	public int deleteMember(String memberId) throws DatabaseException {
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.MEMBER_COLLECTION);
			DBObject query = BasicDBObjectBuilder.start().add(DBCollectionAttributes.MEMBER_ID, memberId).get();
			WriteResult result = col.remove(query);
			if(result.isUpdateOfExisting()) {
				return 1;
			}
			// TODO delete associated user
		} catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
		return 0;
	}

	/*
	 * public boolean updateMemberById(String memberId) throws DatabaseException {
	 * boolean wasUpdated = false; try { col =
	 * MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.MEMBER_COLLECTION);
	 * DBObject query =
	 * BasicDBObjectBuilder.start().add(DBCollectionAttributes.MEMBER_ID, memberId)
	 * .get(); DBCursor cursor = col.find(query); WriteResult updateResult = null;
	 * if(cursor.size() > 0) { DBObject existingMember = cursor.next();
	 * existingMember.put(DBCollectionAttributes.IS_USER, true); updateResult =
	 * col.update(query,existingMember); } if(updateResult.isUpdateOfExisting()) {
	 * wasUpdated = true; } return wasUpdated; }catch (MongoTimeoutException e) {
	 * throw new DatabaseException("MongoTimeoutException", e); } catch
	 * (MongoServerException e) { throw new
	 * DatabaseException("MongoServerException", e); } finally { // close resources
	 * MongoDBUtils.releaseResource(); }
	 * 
	 * }
	 */

}
