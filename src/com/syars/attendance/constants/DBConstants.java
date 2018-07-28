package com.syars.attendance.constants;

public class DBConstants {
	public static final String DRIVERNAME = "oracle.jdbc.driver.OracleDriver";
	public static final String URL = "jdbc:oracle:thin:@192.168.0.8:1521:xe";
	public static final String USERNAME = "system";
	public static final String PASSWORD = "Adarsh@5";
	
	// MongoDB Constants command to run to start mongo db server
	// "D:\Installed_Apps\MongoDB\Server\3.6\bin\mongod.exe" --dbpath D:\Installed_Apps\MongoDB\Data
	public static final String LOCAL_HOST = "localhost";
	public static final int MONGODB_PORT_NUMBER = 27017;
	public static final String MONGODB_LOCAL = "local";
	public static final String FEEDBACK_COLLECTION = "Feedback";
	public static final String RECORD_COUNT = "count";
	
	//Constants related to cloudMongoDB
	// Standard URI format: mongodb://[dbuser:dbpassword@]host:port/dbname
	public static final String CLOUD_MONGO_DB_URI = "mongodb://rsdksharma:Adarsh5@ds257981.mlab.com:57981/cloudhost_mlab_mongod";
	public static final String CLOUD_MONGO_DB_NAME = "cloudhost_mlab_mongod";

}
