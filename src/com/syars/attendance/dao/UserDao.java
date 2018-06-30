package com.syars.attendance.dao;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoServerException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.WriteResult;
import com.syars.attendance.constants.DBConstants;
import com.syars.attendance.exceptions.DatabaseException;
import com.syars.attendance.utils.MongoDBUtils;
import com.syars.attendance.vo.MemberVO;
import com.syars.attendance.vo.UserVO;

public class UserDao {
	private MongoClient client = null;
	private DB db = null;
	private DBCollection col = null;


	public UserVO getUserCredentials(String userId) throws  DatabaseException{
		UserVO user = null;
		try {
			col = MongoDBUtils.getMongoDBCollection("RSUsers");
			DBObject query = BasicDBObjectBuilder.start().add("User Id", userId).get();
			DBCursor cursor = col.find(query);
			while(cursor.hasNext()){
				user = new UserVO();
				DBObject result = cursor.next();
				user.setPassword(result.get("Password").toString());
				user.setRole(result.get("User Role").toString());
			}
			
		}catch(MongoTimeoutException e) {
			System.out.println("UserDao class - MongoTimeoutException:"+e.getMessage());
			throw new DatabaseException("MongoTimeoutException", e);
		}catch(MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		}
		finally {
			//close resources
			MongoDBUtils.releaseResource();
		}

		return user;
	}

	public void createUser(UserVO userVo) throws DatabaseException {
		try {
			//db = getMongoClientDB();
			col = MongoDBUtils.getMongoDBCollection("RSUsers");
			//count = col.getStats().getInt(DBConstants.RECORD_COUNT);
			
			/*createDBObject with index+=1. This object will be in 
			 * JSON format.
			 */
			DBObject userDoc = mapToDBObject(userVo);
			
			//insert created feedback in MongoDB.
			WriteResult writeResult = null;
			writeResult = col.insert(userDoc);
			System.out.println("write result:"+writeResult);
		}catch(MongoTimeoutException e) {
			System.out.println("UserDao class - MongoTimeoutException:"+e.getMessage());
			throw new DatabaseException("MongoTimeoutException", e);
		}catch(MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		}
		finally {
			//close resources
			MongoDBUtils.releaseResource();
		}
		
	}

	public Map<String, UserVO> getAllUsers() throws DatabaseException {
		Map<String, UserVO> userMap = new HashMap<String, UserVO>();
		try {
			col = MongoDBUtils.getMongoDBCollection("RSUsers");
			DBCursor cursor = col.find();
			while(cursor.hasNext()){
				DBObject result = cursor.next();
				userMap.put(result.get("User Id").toString(), map(result, new UserVO()));
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
		return userMap;
	}

	public UserVO getUser(String userId) throws DatabaseException {
		return getAllUsers().get(userId);
	}
	
	public int updateUser(UserVO userVo) throws DatabaseException {
		int updateResult = 0;
		try {
			col = MongoDBUtils.getMongoDBCollection("RSUsers");
			
			//createDBObject. This object will be in JSON format.
			DBObject userDoc = mapToDBObject(userVo);
			
			//create query
			DBObject query = BasicDBObjectBuilder.start().add("User Id", (userVo.getUserId())).get();
			
			//insert created feedback in MongoDB.
			WriteResult writeResult = col.update(query,userDoc);
			System.out.println("write result for update User:"+writeResult);
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

	public void deleteUser(String userId) throws DatabaseException {
		try {
			col = MongoDBUtils.getMongoDBCollection("RSUsers");
			DBObject query = BasicDBObjectBuilder.start().add("User Id", userId).get();
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
	
	private static DBObject mapToDBObject(UserVO userVo) {
		BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

		// docBuilder.append("_id", count);
		docBuilder.append("User Id", userVo.getUserId());
		docBuilder.append("Password", userVo.getPassword());
		docBuilder.append("User Role", userVo.getRole());

		return docBuilder.get();
	}
	
	private UserVO map(DBObject result, UserVO userVO) {
		userVO.setUserId(result.get("User Id").toString());
		userVO.setPassword(result.get("Password").toString());
		userVO.setRole(result.get("User Role").toString());
		
		return userVO;
	}

}
