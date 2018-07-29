package com.syars.attendance.applications.clients;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoServerException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.WriteResult;
import com.syars.attendance.constants.DBCollectionAttributes;
import com.syars.attendance.dao.MemberDao;
import com.syars.attendance.dao.UserDao;
import com.syars.attendance.exceptions.DatabaseException;
import com.syars.attendance.mappers.MemberMapper;
import com.syars.attendance.mappers.UserMapper;
import com.syars.attendance.utils.MongoDBUtils;
import com.syars.attendance.vo.MemberVO;
import com.syars.attendance.vo.UserVO;

public class MongoClient {

	public static void main(String[] args) throws Exception {
		MemberVO memberVo = new MemberVO();
		memberVo.setMemberID("MSEC_1");
		memberVo.setFathersUID("kolkaya");
		memberVo.setMothersUID("kolkaya");
		//updateMember(memberVo);
		MemberDao memberDao = new MemberDao();
		//memberDao.updateMember(memberVo);
		
		
		
		
		UserVO userVo = new UserVO();
		userVo.setMemberId("MSEC_2");
		//userVo.setUserIdCustomized(true);
		userVo.setCustomizedUserId("rsdKsharma");
		userVo.setPassword("Adarsh@6");
		//userVo.setUserRole("ADMIN");
		//updateUser(userVo);
		UserDao userDao = new UserDao();
		userDao.updateUser(userVo);
	}

	public static int updateMember(MemberVO memberVo) throws DatabaseException {
		DBCollection col = null;
		MemberMapper mapper = new MemberMapper();
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

	public static int updateUser(UserVO userVo) throws DatabaseException {
		DBCollection col = null;
		UserMapper mapper = new UserMapper();
		int updateResult = 0;
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.USER_COLLECTION);

			// createDBObject. This object will be in JSON format.
			BasicDBObject userDoc = new BasicDBObject();
			userDoc.put("$set", mapper.doMap(userVo));

			if (userDoc.containsField(DBCollectionAttributes.IS_USER_ID_CUSTOMIZED)) {
				userDoc.removeField(DBCollectionAttributes.USER_ID);
			}

			// create query
			DBObject query = BasicDBObjectBuilder.start().add(DBCollectionAttributes.MEMBER_ID, (userVo.getMemberId()))
					.get();

			// update the details
			WriteResult writeResult = col.update(query, userDoc);
			System.out.println("write result for update User:" + writeResult);
			if (writeResult.isUpdateOfExisting()) {
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

}
