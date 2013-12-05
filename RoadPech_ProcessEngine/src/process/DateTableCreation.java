package process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import dbConnection.DBConnection;
/*
 * Author : Malinga Perera
 * Class  : DateTableCreation
 * 
 * This class is used to create data tables. In simple this will divide the full data table into sub tables
 * each containing records for a specific day. If there are data related to 3 days, 3 tables will be created   
 */
public class DateTableCreation {
    
	public DateTableCreation(Statement statement, String dataBaseName, String username, String password,
	    String prefixForNewTable, String fromTableName) throws Exception {
	    	DBConnection connectionCreater = new DBConnection();
	    	connect = connectionCreater.getConnection(dataBaseName, username, password);
        	this.statement = statement;
        	this.dataBaseName = dataBaseName;
        	this.username = username;
        	this.password = password;
        	this.prefixForNewTable = prefixForNewTable;
        	this.fromTableName = fromTableName;
	}
	
	public DateTableCreation(String dataBaseName, String username, String password, String prefixForNewTable,
		    String fromTableName) throws Exception {
	    	this(null, dataBaseName, username, password, prefixForNewTable, fromTableName);
	}
	
	public DateTableCreation() throws Exception {
	    this("roadpech_mobile", "root", "", "dataset_filtered_date_", "dataset_filtered3");
	}

	private static Connection connect = null;
	private Statement statement = null;
	private String dataBaseName;			// Name of the database
	private String username;			// UserName of the above database
	private String password;			// Password of the above database
	private String prefixForNewTable;		// Date tables will be created with name prefix + date
	private String fromTableName;

	
/*
 * This method will create a table for a given day with the name prefix + date and will fill it with all the records that belongs 
 * to that date	
 */
	public void createDateTable(String date) throws Exception {
		String dbName = prefixForNewTable+date;
		String query1 = "CREATE TABLE IF NOT EXISTS `"+dbName+"` (\n  `imsi` varchar(20) NOT NULL,\n  `date` date NOT NULL,\n  `time` time NOT NULL,\n  `cgi` varchar(20) NOT NULL\n) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String query2 = "INSERT INTO `"+dbName+"`\nSELECT *\nFROM `"+fromTableName+"`\nWHERE `"+fromTableName+"`.`date`='"+date+"';";
		System.out.println(query1);
		System.out.println(query2);
		statement = connect.createStatement();
	    statement.execute(query1);
	    statement.execute(query2);
	}
	
/*
 * This method will retrieve all distinct dates from the main table and will call the above method to create tables for each of
 * these days
 */
	public void createDateTables() throws Exception {
		String query1 = "select distinct `date`\nfrom `"+fromTableName+"`\n";
		statement = connect.createStatement();
		ResultSet resultSet = statement.executeQuery(query1);
		while(resultSet.next()){
			this.createDateTable(resultSet.getString("date"));
		}
	}
	
	/**
	 * @return the dataBaseName
	 */
	public String getDataBaseName() {
	    return dataBaseName;
	}

	/**
	 * @param dataBaseName the dataBaseName to set
	 */
	public void setDataBaseName(String dataBaseName) {
	    this.dataBaseName = dataBaseName;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
	    return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
	    this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
	    return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
	    this.password = password;
	}

	/**
	 * @return the prefixForNewTable
	 */
	public String getPrefixForNewTable() {
	    return prefixForNewTable;
	}

	/**
	 * @param prefixForNewTable the prefixForNewTable to set
	 */
	public void setPrefixForNewTable(String prefixForNewTable) {
	    this.prefixForNewTable = prefixForNewTable;
	}

	/**
	 * @return the fromTableName
	 */
	public String getFromTableName() {
	    return fromTableName;
	}

	/**
	 * @param fromTableName the fromTableName to set
	 */
	public void setFromTableName(String fromTableName) {
	    this.fromTableName = fromTableName;
	}
}
