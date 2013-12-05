package dbConnection;

import java.sql.*;
/*
 * Author : Malinga Perera
 * Class  : DBConnection
 * 
 * This class is used to create database connections to the main database. This will be used in all
 * other classes to create a database connection
 */
public class DBConnection {
	private Connection connect = null;
	private static String dataBaseName = "roadpech_data";			// Name of the database
        private static String username = "root";				// UserName of the above database
        private static String password = "";					// Password of the above database
	
/*
 * This method uses default database name, username and password to create a database connection. If there is a change in the 
 * database name, username or password, below method can be used.	
 */
	public Connection getConnection() throws Exception {
		try {
			return this.getConnection(dataBaseName, username, password);
		}catch (Exception e) {
			      throw e;
		} 
	}
	
/*
 * This method uses given database name, username and password to create a database connection.Aove method also uses this method 
 * to create database connection. 	
 */
	public Connection getConnection(String dataBaseName, String username, String password) throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/"+dataBaseName,username, password);
		     return connect;
		}catch (Exception e) {
			      throw e;
		} 
	}
	
}
