package com.syars.attendance.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.syars.attendance.constants.DBCollectionAttributes;
import com.syars.attendance.constants.DBConstants;

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

	public static void createSequence(String sequenceName) {
		DBCollection col = MongoDBUtils.getMongoDBCollection(sequenceName);
		BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
		docBuilder.append("_id", "item_id");
		docBuilder.append("sequence_value", 0);
		col.insert(docBuilder.get());
	}

	public static int getNextValue(String sequenceName) {
		DBCollection col = MongoDBUtils.getMongoDBCollection(sequenceName);
		BasicDBObject find = new BasicDBObject();
		find.put("_id", "item_id"); // sequenceOfName = item_id
		BasicDBObject update = new BasicDBObject();
		update.put("$inc", new BasicDBObject(DBCollectionAttributes.SEQUENCE_VALUE, 1));
		DBObject obj = col.findAndModify(find, update);

		return (int) obj.get("sequence_value") + 1;
	}

}
