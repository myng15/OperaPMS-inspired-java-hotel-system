package hotel_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.mysql.cj.jdbc.MysqlDataSource;

public class MY_CONNECTION {
	
	public Connection createConnection() {
		Connection connection = null;
//		1. Alternative:		
//		String dbName = "java_hotel_db";
//		String dbUserName = "root";
//		String dbPassword = "root";
//		String connectionString = "jdbc:mysql://127.0.0.1:8889/" + dbName + "?user=" + dbUserName + "&password=" + dbPassword + "&useUnicode=true&characterEncoding=UTF-8";

//		3. Alternative:
		String url = "jdbc:mysql://localhost:8889/java_hotel_db";
		
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			1. Alternative:
//			connection = DriverManager.getConnection(connectionString);

//			2. Alternative:
//			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:8889/java_hotel_db","root", "root");

//			3. Alternative:			
			connection = DriverManager.getConnection(url, "root", "root"); //This try-with-resources (since Java 7) statement automatically calls the close() method of the Connection object once program finishes. It’s cleaner and more elegant. However…It is not secure as well as flexible because the database parameters are hard coded inside the code 
			
		} catch (SQLException ex) {
			Logger.getLogger(" Get Connection -> " + MY_CONNECTION.class.getName()).log(Level.SEVERE, null, ex);
		}
		
//		Connection connection = null;
//		MysqlDataSource mds = new MysqlDataSource();
//		mds.setServerName("localhost");
//		mds.setPortNumber(8889);
//		mds.setUser("root");
//		mds.setPassword("root");
//		mds.setDatabaseName("java_hotel_db");
//		
//		try {
//			connection = mds.getConnection();
//		} catch (SQLException ex) {
//			Logger.getLogger(" Get Connection -> " + MY_CONNECTION.class.getName()).log(Level.SEVERE, null, ex);
//		}
//		
		return connection;
	}
}
