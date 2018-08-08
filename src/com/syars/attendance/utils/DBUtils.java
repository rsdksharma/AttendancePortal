/**
Copyright: SYARS
2018

File Name: DBUtils.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/

package com.syars.attendance.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mongodb.MongoClient;
import com.syars.attendance.constants.DBConstants;

public class DBUtils {

	public static Connection getConnection() {

		Connection con = null;

		try {
			Class.forName(DBConstants.DRIVERNAME);
			con = DriverManager.getConnection(DBConstants.URL, DBConstants.USERNAME, DBConstants.PASSWORD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		return con;

	}

	public static void releaseResource(Connection con, PreparedStatement ps, ResultSet rs) {
		closeConnection(con);
		closePreparedStatement(ps);
		closeResultSet(rs);
	}
	public static void closePreparedStatement(PreparedStatement ps) {

		if (ps != null) {

			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}

	}

	public static void closeConnection(Connection con) {

		if (con != null) {

			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}

	}
	public static void closeResultSet(ResultSet rs) {

		if (rs != null) {

			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}

	}
	public static void releaseResource(MongoClient client) {
		if(client != null) {
			client.close();
		}
		
	}


}

