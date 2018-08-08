/**
Copyright: SYARS
2018

File Name: MongoDBUtils.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/

package com.syars.attendance.utils;

import java.util.concurrent.CountDownLatch;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.syars.attendance.constants.DBCollectionAttributes;
import com.syars.attendance.constants.DBConstants;
import com.ulok.inf.logger.MessageLogger;

public class MongoDBUtils {
	private static MongoClient client = null;
	private static DB db = null;

	public static DBCollection getMongoDBCollection(String collectionName) {
		db = initializeDB();
		return db.getCollection(collectionName);
	}

	public static DB initializeDB() {
        MongoClientURI uri  = new MongoClientURI(DBConstants.CLOUD_MONGO_DB_URI);
		client = new MongoClient(uri);
		return client.getDB(DBConstants.CLOUD_MONGO_DB_NAME);
	}

	public static void releaseResource() {
		if (client != null) {
			client.close();
		}
	}

	public static void createSequence(String sequenceName) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		DBCollection col = MongoDBUtils.getMongoDBCollection(sequenceName);
		BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
		docBuilder.append("_id", "item_id");
		docBuilder.append("sequence_value", 0);
		col.insert(docBuilder.get());
		latch.await();
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
	
	public void testMessageLogger() {
		MessageLogger.logMethodEntry(this, "testMessageLogger");
		try {
			int a = 10/0;
		}catch(Exception e) {
			MessageLogger.logError(this, "testMessageLogger", null, "Error log testing", e);
		}
	}

}
