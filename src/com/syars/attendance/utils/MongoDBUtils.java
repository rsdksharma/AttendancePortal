package com.syars.attendance.utils;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.syars.attendance.constants.DBConstants;
import com.syars.attendance.vo.MemberVO;
import com.syars.attendance.vo.UserVO;

public class MongoDBUtils {
	private static MongoClient client = null;
	private static DB db = null;

	public static DBCollection getMongoDBCollection(String collectionName) {
		db = initializeDB();
		return db.getCollection(collectionName);
	}

	public static DB initializeDB() {
		// get MongoDB client named localhost running in port 27017
		client = new MongoClient(DBConstants.LOCAL_HOST, DBConstants.MONGODB_PORT_NUMBER);
		/*
		 * get DB with name local. If local is not present then it will be automatically
		 * created.
		 */
		return client.getDB(DBConstants.MONGODB_LOCAL);

	}

	public static void releaseResource() {
		if (client != null) {
			client.close();
		}
	}

	public static DBObject createUserDBObject(int count, UserVO userVo) {
		BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

		// docBuilder.append("_id", count);
		docBuilder.append("User Id", userVo.getUserId());
		docBuilder.append("Password", userVo.getPassword());
		docBuilder.append("User Role", userVo.getRole());

		return docBuilder.get();
	}

	public static DBObject createMemberDBObject(DBCollection col, MemberVO memberVo) {
		int count = 0;
		if (col.getStats() != null) {
			count = col.getStats().getInt(DBConstants.RECORD_COUNT);
		}
		BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

		// docBuilder.append("_id", "MEMBER_"+count); //think for creating unique id
		// docBuilder.append("Branch UID", memberVo.getBranchUIDNumber());
		// docBuilder.append("Global UID", memberVo.getGlobalUIDNumber());
		docBuilder.append("FullName", memberVo.getFullName());
		docBuilder.append("Mobile", memberVo.getMobileNumber());
		docBuilder.append("Email", memberVo.getEmailId());

		return docBuilder.get();
	}

}
