package com.syars.attendance.tests.sampleTests;

import com.syars.attendance.constants.DBCollectionAttributes;
import com.syars.attendance.utils.MongoDBUtils;

public class CreateSequence {

	public static void main(String[] args) throws Exception {
		MongoDBUtils.createSequence(DBCollectionAttributes.MEMBER_SEQUENCE);

		System.out.println(">>>Current sequence number:"+MongoDBUtils.getNextValue(DBCollectionAttributes.MEMBER_SEQUENCE));

	}
	

}
