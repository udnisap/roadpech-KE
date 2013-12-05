package process;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import dbConnection.DBConnection;

/*
 * Author : Malinga Perera
 * Class  : FilterByLocationUpdateFreq
 * 
 * When looked into some users we could see that they have more than one
 * location update but those are separated by hours of time. This cannot be
 * due to heavy traffic because we are targeting a small area that should be
 * able to pass though in 20 minutes even in the heavy traffic. With above
 * assumption, it can be inferred that these points do not belong to users
 * who is driving a vehicle. Therefore, we decided to remove users that do
 * not have at least a single pair of frequent location updates.
 */
public class FilterByLocationUpdateFreq extends Setup {
    private boolean debugEnabled=false;

    public FilterByLocationUpdateFreq(boolean editExistingTable, String newTableName, String tableToFilter,
	    String tempTableName, String minFreq) {
	super();
	this.editExistingTable = editExistingTable;
	this.newTableName = newTableName;
	this.tableToFilter = tableToFilter;
	this.tempTableName = tempTableName;
	this.minFreq = minFreq;
    }

    public FilterByLocationUpdateFreq() {

    }

    private boolean editExistingTable = true;
    private String newTableName = "dataset_filtered_date_new16";
    private String tableToFilter = "dataset_filtered_date16";
    private String tempTableName = "to_be_filtered";

    /*
     * user without a single pair of location updates within a minFreq time
     * window will be filtered
     */
    private String minFreq = "00:15:00";

    public static void main(String[] args) throws Exception {

    }

    /*
     * THis method will remove users that do not have at least a single pair of
     * frequent location updates. This method will use the below supporting
     * methods
     */
    public void filterByFreq() throws Exception {
	DBConnection connectionCreater = new DBConnection();
	connect = connectionCreater.getConnection(dataBaseName, username, password);
	this.createTempTable();
	this.insertDataToTempTable();
	if (editExistingTable) {
	    String query = "DELETE FROM `" + dataBaseName + "`.`" + tableToFilter + "`\r\nWHERE `" + tableToFilter
		    + "`.`imsi` IN (SELECT `imsi`\r\nFROM `" + dataBaseName + "`.`" + tempTableName + "`);";
	    debugPrint(query,"Filtering by frequency");
	    statement = connect.createStatement();
	    statement.execute(query);
	} else {
	    this.createNewTable(newTableName);
	    String query = "INSERT INTO `" + dataBaseName + "`.`" + newTableName + "`\r\nSELECT *\r\nFROM `"
		    + tableToFilter + "`\r\nWHERE `" + tableToFilter + "`.`imsi` NOT IN (SELECT `imsi` FROM `"
		    + dataBaseName + "`.`" + tempTableName + "`);";
	    debugPrint(query,"Inserting filtered data into table");
	    statement = connect.createStatement();
	    statement.execute(query);
	}
    }

    /*
     * This will create a temporary table and add the imsi values that should be
     * filtered to it.
     */
    public void createTempTable() throws SQLException {
	String query = "DROP TABLE IF EXISTS `" + tempTableName + "`;";
	debugPrint(query,"Drop temporary table");
	statement = connect.createStatement();
	statement.execute(query);

	query = "CREATE TABLE IF NOT EXISTS `" + tempTableName
		+ "` (\r\n  `imsi` varchar(20) NOT NULL,\r\nKEY `imsi` (`imsi`)\r\n) ENGINE=InnoDB DEFAULT CHARSET=latin1;\r\n";
	debugPrint(query,"Creating temporary table");
	statement.execute(query);
    }

    public void insertDataToTempTable() throws SQLException {
	String query = "INSERT INTO `"
		+ dataBaseName
		+ "`.`"
		+ tempTableName
		+ "`\r\nSELECT t1.`imsi`\r\nFROM `"
		+ dataBaseName
		+ "`.`"
		+ tableToFilter
		+ "` AS t1\r\nJOIN `"
		+ dataBaseName
		+ "`.`"
		+ tableToFilter
		+ "` AS t2 \r\nON t1.`imsi` = t2.`imsi` AND t1.`time`> t2.`time`\r\nGROUP BY t1.`imsi`\r\nHAVING MIN(TIMEDIFF(t1.`time`, t2.`time`)) > '"
		+ minFreq + "';";
	debugPrint(query,"");
	statement = connect.createStatement();
	statement.execute(query);
    }

    public void createNewTable(String tableName) throws SQLException {
	String query = "\r\nCREATE TABLE IF NOT EXISTS `"
		+ tableName
		+ "` (\r\n  `imsi` varchar(20) NOT NULL,\r\n  `date` date NOT NULL,\r\n  `time` time NOT NULL,\r\n  `cgi` varchar(20) NOT NULL\r\n) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
	debugPrint(query,"Creating new table");
	statement = connect.createStatement();
	statement.execute(query);
    }
    
    public void debugPrint(String query, String msg){
        if(debugEnabled){
            System.out.println(query);
        } else {
            System.out.println(msg);
        }
    }

    /**
     * @return the connect
     */
    public Connection getConnect() {
	return connect;
    }

    /**
     * @param connect
     *            the connect to set
     */
    public void setConnect(Connection connect) {
	this.connect = connect;
    }

    /**
     * @return the statement
     */
    public Statement getStatement() {
	return statement;
    }

    /**
     * @param statement
     *            the statement to set
     */
    public void setStatement(Statement statement) {
	this.statement = statement;
    }

    /**
     * @return the dataBaseName
     */
    public String getDataBaseName() {
	return dataBaseName;
    }

    /**
     * @param dataBaseName
     *            the dataBaseName to set
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
     * @param username
     *            the username to set
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
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * @return the editExistingTable
     */
    public boolean isEditExistingTable() {
	return editExistingTable;
    }

    /**
     * @param editExistingTable
     *            the editExistingTable to set
     */
    public void setEditExistingTable(boolean editExistingTable) {
	this.editExistingTable = editExistingTable;
    }

    /**
     * @return the newTableName
     */
    public String getNewTableName() {
	return newTableName;
    }

    /**
     * @param newTableName
     *            the newTableName to set
     */
    public void setNewTableName(String newTableName) {
	this.newTableName = newTableName;
    }

    /**
     * @return the tableToFilter
     */
    public String getTableToFilter() {
	return tableToFilter;
    }

    /**
     * @param tableToFilter
     *            the tableToFilter to set
     */
    public void setTableToFilter(String tableToFilter) {
	this.tableToFilter = tableToFilter;
    }

    /**
     * @return the tempTableName
     */
    public String getTempTableName() {
	return tempTableName;
    }

    /**
     * @param tempTableName
     *            the tempTableName to set
     */
    public void setTempTableName(String tempTableName) {
	this.tempTableName = tempTableName;
    }

    /**
     * @return the minFreq
     */
    public String getMinFreq() {
	return minFreq;
    }

    /**
     * @param minFreq
     *            the minFreq to set
     */
    public void setMinFreq(String minFreq) {
	this.minFreq = minFreq;
    }

}
