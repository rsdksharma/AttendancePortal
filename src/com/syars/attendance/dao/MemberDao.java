package com.syars.attendance.dao;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoServerException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.WriteResult;
import com.syars.attendance.constants.DBConstants;
import com.syars.attendance.exceptions.DatabaseException;
import com.syars.attendance.utils.MongoDBUtils;
import com.syars.attendance.vo.MemberVO;

public class MemberDao {
	private DBCollection col = null;
	
	public void createMember(MemberVO memberVo) throws DatabaseException {
		try {
			col = MongoDBUtils.getMongoDBCollection("RSMembers");
			
			//createDBObject. This object will be in JSON format.
			DBObject memberDoc = mapToDBObject(memberVo);
			
			//insert created feedback in MongoDB.
			WriteResult writeResult = col.insert(memberDoc);
			if(writeResult.wasAcknowledged()) {
				System.out.println(">>>>insertion acknowledged");
			}
		
		}catch(MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		}catch(MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		}
		finally {
			//close resources
			MongoDBUtils.releaseResource();
		}
	}

	public Map<String, MemberVO> getAllMembers() throws DatabaseException {
		Map<String, MemberVO> memberMap = new HashMap<String, MemberVO>();
		try {
			col = MongoDBUtils.getMongoDBCollection("RSMembers");
			DBCursor cursor = col.find();
			while(cursor.hasNext()){
				DBObject result = cursor.next();
				memberMap.put(result.get("Mobile").toString(), map(result, new MemberVO()));
			}
		}catch(MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		}catch(MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		}
		finally {
			//close resources
			MongoDBUtils.releaseResource();
		}
		return memberMap;
	}
	
	public MemberVO getMember(String memberId) throws DatabaseException {
		return getAllMembers().get(memberId);
	}
	

	public int updateMember(MemberVO memberVo) throws DatabaseException{
		int updateResult = 0;
		try {
			col = MongoDBUtils.getMongoDBCollection("RSMembers");
			
			//createDBObject. This object will be in JSON format.
			DBObject memberDoc = mapToDBObject(memberVo);
			
			//create query
			DBObject query = BasicDBObjectBuilder.start().add("Mobile", (memberVo.getMobileNumber())).get();
			
			//insert created feedback in MongoDB.
			WriteResult writeResult = col.update(query,memberDoc);
			System.out.println("write result for update member:"+writeResult);
			if(writeResult.isUpdateOfExisting()) {
				updateResult = 1;
			}
		
		}catch(MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		}catch(MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		}
		finally {
			//close resources
			MongoDBUtils.releaseResource();
		}
		return updateResult;
	}
	
	public void deleteMember(String memberId) throws DatabaseException{
		try {
			col = MongoDBUtils.getMongoDBCollection("RSMembers");
			DBObject query = BasicDBObjectBuilder.start().add("Mobile", memberId).get();
			col.remove(query);
		}catch(MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		}catch(MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		}
		finally {
			//close resources
			MongoDBUtils.releaseResource();
		}
	}
	private DBObject mapToDBObject(MemberVO memberVo) {
		int count = 0;
		if(col.getStats() != null) {
			count = col.getStats().getInt(DBConstants.RECORD_COUNT);
		}
		BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

		//docBuilder.append("_id", "MEMBER_"+count); //think for creating unique id
		//docBuilder.append("Branch UID", memberVo.getBranchUIDNumber());
		//docBuilder.append("Global UID", memberVo.getGlobalUIDNumber());
		docBuilder.append("FullName", memberVo.getFullName());
		docBuilder.append("Mobile", memberVo.getMobileNumber());
		docBuilder.append("Email", memberVo.getEmailId());

		return docBuilder.get();
	}
	
	private MemberVO map(DBObject result, MemberVO memberVo) {
		//memberVo.setBranchUIDNumber(result.get("").toString());
		//memberVo.setBranch(result.get("").toString());
		//memberVo.setDateOfBirth(result.get("").toString());
		memberVo.setFullName(result.get("FullName").toString());
		memberVo.setMobileNumber(result.get("Mobile").toString());
		memberVo.setEmailId(result.get("Email").toString());
		//memberVo.setFirstName(result.get("").toString());
		//memberVo.setLastName(result.get("").toString());
		return memberVo;
	}

}
