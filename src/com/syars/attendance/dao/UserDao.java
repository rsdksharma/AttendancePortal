package com.syars.attendance.dao;

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
import com.syars.attendance.utils.DBUtils;
import com.syars.attendance.utils.MongoDBUtils;
import com.syars.attendance.vo.UserVO;

public class UserDao {
	private MongoClient client = null;
	private DB db = null;
	private DBCollection col = null;


	public UserVO getUserCredentials(String userId) {
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

		return user;
	}

	public void createUser(UserVO userVo) {
		int count = 0;
		try {
			//db = getMongoClientDB();
			col = MongoDBUtils.getMongoDBCollection("RSUsers");
			count = col.getStats().getInt(DBConstants.RECORD_COUNT);
			
			/*createDBObject with index+=1. This object will be in 
			 * JSON format.
			 */
			DBObject userDoc = MongoDBUtils.createUserDBObject(count+1,userVo);
			
			//insert created feedback in MongoDB.
			WriteResult writeResult = null;
			writeResult = col.insert(userDoc);
			System.out.println("write result:"+writeResult);
		}
		catch(MongoServerException e) {
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

}
