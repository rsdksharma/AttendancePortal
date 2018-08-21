package com.syars.attendance.applications.clients;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoServerException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.WriteResult;
import com.syars.attendance.constants.DBCollectionAttributes;
import com.syars.attendance.dao.MemberDao;
import com.syars.attendance.dao.PresenceDao;
import com.syars.attendance.dao.UserDao;
import com.syars.attendance.exceptions.DatabaseException;
import com.syars.attendance.mappers.MemberMapper;
import com.syars.attendance.mappers.UserMapper;
import com.syars.attendance.resources.AttendanceResource;
import com.syars.attendance.resources.MemberResource;
import com.syars.attendance.utils.DateFormatter;
import com.syars.attendance.utils.MongoDBUtils;
import com.syars.attendance.vo.MemberVO;
import com.syars.attendance.vo.PresenceVO;
import com.syars.attendance.vo.UserVO;
import com.thoughtworks.xstream.XStream;

public class MongoClient {
	public static DBCollection col = null;

	public static void main(String[] args) throws Exception {
		/*MemberVO memberVo = new MemberVO();
		memberVo.setMemberID("MSEC_1");
		memberVo.setFathersUID("kolkaya");
		memberVo.setMothersUID("kolkaya");
		//updateMember(memberVo);
		MemberDao memberDao = new MemberDao();
		//memberDao.updateMember(memberVo);
		*/
		
		
		
		/*UserVO userVo = new UserVO();
		userVo.setMemberId("MSEC_2");
		//userVo.setUserIdCustomized(true);
		userVo.setCustomizedUserId("rsdKsharma");
		userVo.setPassword("Adarsh@6");
		//userVo.setUserRole("ADMIN");
		//updateUser(userVo);
		UserDao userDao = new UserDao();
		userDao.updateUser(userVo);*/
		PresenceDao presenceDao = new PresenceDao();
		MemberDao memberDao = new MemberDao();
		UserDao userDao = new UserDao();
		MemberResource MemberResource = new MemberResource();
		AttendanceResource atendanceResource = new AttendanceResource();
		PresenceVO vo = new PresenceVO();
		vo.setMemberId("MSEC_2");
		//presenceDao.insertPresence(vo);
		//System.out.println(">>>>ALL PRESENCE:"+ new XStream().toXML(presenceDao.retrievePresenceForAll()));
		//System.out.println(">>>>result:"+ new XStream().toXML(presenceDao.extractUniqueMembers()));
		System.out.println(">>>>result:"+ new XStream().toXML(atendanceResource.insertPresence(vo)));
		
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
