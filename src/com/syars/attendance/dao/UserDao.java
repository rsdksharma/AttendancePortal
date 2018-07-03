package com.syars.attendance.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
				user = mapper.mapCredentials(result, new UserVO());
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

	public void registerMemberAsUser(UserVO userVo) throws DatabaseException {
		try {
			if (!isMemberRegistered(userVo.getMemberId())) {
				throw new DatabaseException("Member not registered", null);
			}
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.USER_COLLECTION);
			DBObject userDoc = mapper.doMap(userVo);
			if(userDoc.containsField(DBCollectionAttributes.IS_USER_ID_CUSTOMIZED)) {
				userDoc.removeField(DBCollectionAttributes.USER_ID);
			}
			else {
				userDoc.put(DBCollectionAttributes.USER_ID, createUniqueUserId(userVo));
			}
			// insert user in RSUserCollection
			WriteResult writeResult = col.insert(userDoc);
			if (!writeResult.wasAcknowledged()) {
				throw new DatabaseException("could not register member as user", null);
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

	}

	private Object createUniqueUserId(UserVO userVo) {
		String uniqueID = UUID.randomUUID().toString();
		System.out.println(">>>>uniqueID:"+uniqueID);
		// TODO create unique userId, it is default as of now
		return AttendanceConstants.DEFAULT_USER_ID;
	}

	private boolean isMemberRegistered(String memberId) throws DatabaseException {
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.MEMBER_COLLECTION);

			// create query
			DBObject query = BasicDBObjectBuilder.start().add(DBCollectionAttributes.MEMBER_ID, memberId).get();

			DBCursor cursor = col.find(query);
			// if result found then update member details with IS_USER = true and return
			// true
			if (cursor.size() > 0) {
				DBObject existingMember = cursor.next();
				existingMember.put(DBCollectionAttributes.IS_USER, true);
				WriteResult updateResult = col.update(query, existingMember);
				if (updateResult.isUpdateOfExisting()) {
					return true;
				}
			}
			return false;

		} catch (MongoTimeoutException e) {
			System.out.println("UserDao class - MongoTimeoutException:" + e.getMessage());
			throw new DatabaseException("MongoTimeoutException", e);
		} catch (MongoServerException e) {
			throw new DatabaseException("MongoServerException", e);
		} finally {
			// close resources
			MongoDBUtils.releaseResource();
		}
	}

	public Map<String, UserVO> getAllUsers() throws DatabaseException {
		Map<String, UserVO> userMap = new HashMap<String, UserVO>();
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.MEMBER_COLLECTION);
			DBCursor cursor = col.find();
			while (cursor.hasNext()) {
				DBObject result = cursor.next();
				userMap.put(result.get(DBCollectionAttributes.MEMBER_ID).toString(),
						mapper.doMap(result, new UserVO()));
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

	public int updateUser(UserVO userVo) throws DatabaseException {
		int updateResult = 0;
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.USER_COLLECTION);

			// createDBObject. This object will be in JSON format.
			DBObject userDoc = mapper.doMap(userVo);
			if (userDoc.containsKey(DBCollectionAttributes.IS_USER_ID_CUSTOMIZED)) {
				userDoc.removeField(DBCollectionAttributes.USER_ID);
			}

			// create query
			DBObject query = BasicDBObjectBuilder.start().add(DBCollectionAttributes.MEMBER_ID, (userVo.getMemberId()))
					.get();

			// insert created feedback in MongoDB.
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

	public void deleteUser(String userId) throws DatabaseException {
		try {
			col = MongoDBUtils.getMongoDBCollection(DBCollectionAttributes.USER_COLLECTION);
			DBObject query = BasicDBObjectBuilder.start().add(DBCollectionAttributes.USER_ID, userId).get();
			col.remove(query);
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
