package com.syars.attendance.dao;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoServerException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.WriteResult;
import com.syars.attendance.constants.DBConstants;
import com.syars.attendance.utils.DBUtils;
import com.syars.attendance.utils.MongoDBUtils;
import com.syars.attendance.vo.MemberVO;

public class MemberDao {
	private DBCollection col = null;
	
	public void createMember(MemberVO memberVo) {
		MongoClient client = null;
		try {
			col = MongoDBUtils.getMongoDBCollection("RSMembers");
			
			//createDBObject. This object will be in JSON format.
			DBObject memberDoc = mapToDBObject(memberVo);
			
			//insert created feedback in MongoDB.
			WriteResult writeResult = null;
			writeResult = col.insert(memberDoc);
			System.out.println("write result:"+writeResult);
		
		}catch(MongoServerException e) {
			System.out.println("MongoServerException:"+e);
			e.printStackTrace();
		}catch(MongoTimeoutException ex) {
			System.out.println("Timeout exception:"+ex);
			ex.printStackTrace();
		}catch(Exception e) {
			System.out.println("Unknown exception:"+e);
			e.printStackTrace();
		}
		finally {
			//close resources
			DBUtils.releaseResource(client);
		}
	}

	public Map<String, MemberVO> getAllMembers() {
		Map<String, MemberVO> memberMap = new HashMap<String, MemberVO>();
		try {
			col = MongoDBUtils.getMongoDBCollection("RSMembers");
			DBCursor cursor = col.find();
			while(cursor.hasNext()){
				DBObject result = cursor.next();
				memberMap.put(result.get("Mobile").toString(), map(result, new MemberVO()));
			}
		}catch(MongoServerException e) {
			System.out.println("MongoServerException:"+e);
			e.printStackTrace();
		}catch(MongoTimeoutException ex) {
			System.out.println("Timeout exception:"+ex);
			ex.printStackTrace();
		}catch(Exception e) {
			System.out.println("Unknown exception:"+e);
			e.printStackTrace();
		}
		finally {
			//close resources
			MongoDBUtils.releaseResource();
		}
		return memberMap;
	}
	
	public MemberVO getMember(String memberId) {
		return getAllMembers().get(memberId);
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
