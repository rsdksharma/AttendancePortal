/**
Copyright: SYARS
2018

File Name: UserDao.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/

package com.syars.attendance.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

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
import com.syars.attendance.exceptions.DatabaseException;
import com.syars.attendance.mappers.UserMapper;
import com.syars.attendance.utils.MongoDBUtils;
import com.syars.attendance.vo.UserVO;

public class UserDao {
	private DBCollection col = null;
	UserMapper mapper = new UserMapper();
	MemberDao memberDao = new MemberDao();

	public UserVO getUserCredentials(String userId) throws DatabaseException {
		UserVO user = null;
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.USER_COLLECTION);
			// at first find customised user id,
			DBObject query = BasicDBObjectBuilder.start().add(DBCollectionAttributes.CUSTOMIZED_USER_ID, userId).get();
			DBCursor cursor = col.find(query);
			// if customised id not found then find normal
			if (!cursor.hasNext()) {
				query = BasicDBObjectBuilder.start().add(DBCollectionAttributes.USER_ID, userId).get();
				cursor = col.find(query);
			}
			// extract the result and map to user
			DBObject result = null;
			// unique user found
			if (cursor.size() == 1) {
				result = cursor.next();
				if (result.get(DBCollectionAttributes.PASSWORD) == null) {
					throw new DatabaseException(AttendanceConstants.NO_PASSWORD, null);
				}
				user = mapper.mapCredentials(result);
			}
			// multiple users found
			else if (cursor.size() > 1) {
				throw new DatabaseException(AttendanceConstants.MULTIPLE_USERS, null);
			}
		} catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}

		return user;
	}

	public String registerMemberAsUser(UserVO userVo) throws DatabaseException {
		String createdUserId = null;
		try {
			if (!memberDao.isMemberRegistered(userVo.getMemberId(), true)) {
				throw new DatabaseException("Member not registered", null);
			}
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.USER_COLLECTION);
			DBObject userDoc = mapper.doMap(userVo);
			String userId = createUniqueUserId(userVo);
			userDoc.put("_id", userVo.getMemberId());
			if (userDoc.containsField(DBCollectionAttributes.IS_USER_ID_CUSTOMIZED)) {
				userDoc.removeField(DBCollectionAttributes.USER_ID);
				userId = userVo.getCustomizedUserId();
			} else {
				userDoc.put(DBCollectionAttributes.USER_ID, userId);
			}
			// insert user in RSUserCollection
			WriteResult writeResult = col.insert(userDoc);
			if (writeResult.wasAcknowledged()) {
				createdUserId = userId;
			}

		} catch (MongoTimeoutException e) {
			System.out.println("UserDao class - MongoTimeoutException:" + e.getMessage());
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
		return createdUserId;

	}

	private String createUniqueUserId(UserVO userVo) {
		String uniqueID = UUID.randomUUID().toString();
		StringTokenizer tokenizer = new StringTokenizer(uniqueID, "-");
		tokenizer.nextToken();
		StringBuilder builder = new StringBuilder();
		builder.append(AttendanceConstants.USER_);
		return builder.append(tokenizer.nextToken()).toString();
	}


	public Map<String, UserVO> getAllUsers() throws DatabaseException {
		Map<String, UserVO> userMap = new HashMap<String, UserVO>();
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.USER_COLLECTION);
			DBCursor cursor = col.find();
			while (cursor.hasNext()) {
				DBObject result = cursor.next();
				if(result.get(DBCollectionAttributes.CUSTOMIZED_USER_ID) != null) {
					userMap.put(result.get(DBCollectionAttributes.CUSTOMIZED_USER_ID).toString(), mapper.doMap(result));
				}
				else if(result.get(DBCollectionAttributes.USER_ID) != null) {
					userMap.put(result.get(DBCollectionAttributes.MEMBER_ID).toString(), mapper.doMap(result));
				}
				else if(result.get(DBCollectionAttributes.MEMBER_ID) != null) {
					userMap.put(result.get(DBCollectionAttributes.MEMBER_ID).toString(), mapper.doMap(result));
				}
			}
		} catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
		return userMap;
	}

	public UserVO getUser(String userId) throws DatabaseException {
		return getAllUsers().get(userId);
	}

	public String updateUser(UserVO userVo) throws DatabaseException {
		String updatedId = null;

		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.USER_COLLECTION);

			// create query
			DBObject query = BasicDBObjectBuilder.start()
						.add(DBCollectionAttributes.MEMBER_ID, (userVo.getMemberId())).get();
			// createDBObject from userVo, This object will be in JSON format.
			DBObject userDoc = mapper.doMap(userVo);
			
			//create document to be updated
			DBObject updateDoc = new BasicDBObject();
			//if customizedUserId found then delete default one which is USER_ID
			if (userDoc.containsField(DBCollectionAttributes.IS_USER_ID_CUSTOMIZED)) {
				updateDoc.put("$unset", new BasicDBObject(DBCollectionAttributes.USER_ID, ""));
			}
			updateDoc.put("$set", userDoc);
			// execute update
			WriteResult writeResult = col.update(query, updateDoc);
			if (writeResult.isUpdateOfExisting()) {
				updatedId = userVo.getMemberId();
			}
		} catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
		return updatedId;
	}

	public void deleteUser(String userId) throws DatabaseException {
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.USER_COLLECTION);
			DBObject query = BasicDBObjectBuilder.start().add(DBCollectionAttributes.USER_ID, userId).get();
			WriteResult result = col.remove(query);
			if (result.isUpdateOfExisting()) {
				// return something
			}
			// TODO update member with isUserAlso = false
		} catch (MongoTimeoutException e) {
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
	}

}
