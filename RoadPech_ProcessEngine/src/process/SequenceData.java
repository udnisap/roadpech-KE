package process;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import dbConnection.DBConnection;

/*
 * Author : Malinga Perera
 * Class  : SequenceData
 * 
 * This class is used for create location sequences for each user. 
 * This step was harder than the above steps and needed lot of processing.
 * Here out goal was to get a cell tower sequence and a time stamp sequence for each user. 
 * When creating sequences, it should be identified as two sequences if 
 * it has a larger time gap between two location updates. 
 * To store these sequences, a new table was required.
 *     
 */
public class SequenceData extends Setup{
    public SequenceData(String newTableName, String tableToFilter, long maxIntervel,
	    boolean removeDuplicateLocationUpdates, int minSequenceLength, boolean executeQuery) {
	super();
	this.newTableName = newTableName;
	this.tableToFilter = tableToFilter;
	this.maxIntervel = maxIntervel;
	this.removeDuplicateLocationUpdates = removeDuplicateLocationUpdates;
	this.minSequenceLength = minSequenceLength;
	this.executeQuery = executeQuery;
    }
    
    public SequenceData() {
        // TODO Auto-generated constructor stub
    }

    private String newTableName = "dataset_filtered_date_new14";
    private String tableToFilter = "dataset_filtered_date14";

    /*
     * If two location updates within a sequence has larger gap (in
     * milliseconds) than this max interval then it should be divided into 2
     * sequences by the same user.
     */
    private long maxIntervel = 20 * 60 * 1000;

    private ResultSet resultSet = null;
    private boolean removeDuplicateLocationUpdates = false;
    private int minSequenceLength = 1;
    private boolean executeQuery = false;

    public static void main(String[] args) throws Exception {
	
	SequenceData sequencer = new SequenceData();
	sequencer.filterBySequenceLength();
    }

    /*
     * This method create location sequences for each user. Here out goal was to
     * get a cell tower sequence and a time stamp sequence for each user. When
     * creating sequences, it should be identified as two sequences if it has a
     * larger time gap between two location updates. To store these sequences, a
     * new table was required. This method will use below method to do the above
     * task
     */
    public void sequenceData() throws Exception {
	DBConnection connectionCreater = new DBConnection();
	connect = connectionCreater.getConnection(dataBaseName, username, password);
	String user = null;
	Time preTime = null;

	String imsi = null;
	String cgi = null;
	String time = null;
	String startTime = null;
	int count = 0;

	this.createNewTable();
	resultSet = this.getOrderedData();
	if (removeDuplicateLocationUpdates) {
	    resultSet = removeDuplicate(resultSet);
	    resultSet.beforeFirst();
	}

	while (resultSet.next()) {
	    String nextUser = resultSet.getString("imsi");
	    if (user != null && user.equalsIgnoreCase(nextUser)) {
		Time nextTime = resultSet.getTime("time");
		if (nextTime.getTime() - preTime.getTime() > maxIntervel) {
		    this.insertIntoNewTable(imsi, cgi, time, startTime, count);
		    count = 1;
		    preTime = nextTime;
		    time = preTime.toString();
		    startTime = time;
		    cgi = resultSet.getString("cgi");
		} else {
		    preTime = nextTime;
		    time = time + ", " + preTime.toString();
		    cgi = cgi + ", " + resultSet.getString("cgi");
		    count++;
		}
	    } else {
		if (imsi != null) {
		    this.insertIntoNewTable(imsi, cgi, time, startTime, count);
		}
		count = 1;
		user = resultSet.getString("imsi");
		imsi = user;
		preTime = resultSet.getTime("time");
		time = preTime.toString();
		startTime = time;
		cgi = resultSet.getString("cgi");
	    }
	}
	this.insertIntoNewTable(imsi, cgi, time, startTime, count);
	this.filterBySequenceLength();
    }

    /*
     * After creating, the table data sorted by the user and the time needed to
     * create the sequence. Retrieving the sorted data from the previously
     * filtered table was done by the following method.
     */
    public ResultSet getOrderedData() throws SQLException {
	String query = "select * from `" + dataBaseName + "`.`" + tableToFilter + "`ORDER BY `"
		+ tableToFilter + "`.`imsi`, `" + tableToFilter + "`.`time` ASC";
	System.out.println(query);
	statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		ResultSet.CONCUR_UPDATABLE);
	if (true)
	    resultSet = statement.executeQuery(query);
	return resultSet;
    }

    /*
     * First, it is required to remove location updates from the same location
     * repeatedly. Form the observations; it was clear that most of the users
     * have several location updates from the same location, which cannot be
     * used for the speed calculation.
     */
    public ResultSet removeDuplicate(ResultSet resultSet) throws SQLException {
	resultSet.beforeFirst();
	String imsi = null;
	String cgi = null;
	while (resultSet.next()) {
	    if (resultSet.getString("imsi").equalsIgnoreCase(imsi)
		    && resultSet.getString("cgi").equalsIgnoreCase(cgi)) {
		resultSet.deleteRow();
	    }
	    imsi = resultSet.getString("imsi");
	    cgi = resultSet.getString("cgi");
	}
	return resultSet;
    }

    /*
     * This new table has two text fields to hold the cell tower sequence and
     * the time stamp sequence. In addition, there is a separate column
     * (‘count’) to hold the length of the sequence (this will be used for the
     * filtering in the next step).
     */
    public void createNewTable() throws SQLException {
	String query = "CREATE TABLE IF NOT EXISTS `"
		+ newTableName
		+ "` (\r\n  `imsi` varchar(20) NOT NULL, \r\n  `start_time` time NOT NULL,\r\n  `cgi` text NOT NULL,\r\n  `time` text NOT NULL,\r\n  `count` int NOT NULL\r\n) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
	System.out.println(query);
	statement = connect.createStatement();
	if (executeQuery)
	    statement.execute(query);
    }

    public void insertIntoNewTable(String imsi, String cgi, String time, String startTime, int count)
	    throws SQLException {
	String query = "INSERT INTO `" + newTableName
		+ "`(`imsi`, `start_time`, `cgi`, `time`, `count`) VALUES (\"" + imsi + "\",\""
		+ startTime + "\",\"" + cgi + "\",\"" + time + "\",\"" + count + "\")";
	// System.out.println(query);
	statement = connect.createStatement();
	if (executeQuery)
	    statement.execute(query);
    }

    /*
     * When observing the sequence table It was seen that there are lot of
     * sequences with length one. It is possible as in the previous step users
     * that do not have at least one pair of frequent updates have been removed.
     * However there can be users with one frequent pair and several other
     * updates with long gaps which has been identified as sequences with length
     * one.
     */
    public void filterBySequenceLength() throws SQLException {
	String query = "DELETE FROM `" + dataBaseName + "`.`" + newTableName + "`\r\nWHERE `"
		+ newTableName + "`.`count` = " + minSequenceLength + ";";
	System.out.println(query);
	statement = connect.createStatement();
	if (executeQuery)
	    statement.execute(query);
    }

    /**
     * @return the newTableName
     */
    public String getNewTableName() {
        return newTableName;
    }

    /**
     * @param newTableName the newTableName to set
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
     * @param tableToFilter the tableToFilter to set
     */
    public void setTableToFilter(String tableToFilter) {
        this.tableToFilter = tableToFilter;
    }

    /**
     * @return the maxIntervel
     */
    public long getMaxIntervel() {
        return maxIntervel;
    }

    /**
     * @param maxIntervel the maxIntervel to set
     */
    public void setMaxIntervel(long maxIntervel) {
        this.maxIntervel = maxIntervel;
    }

    /**
     * @return the removeDuplicateLocationUpdates
     */
    public boolean isRemoveDuplicateLocationUpdates() {
        return removeDuplicateLocationUpdates;
    }

    /**
     * @param removeDuplicateLocationUpdates the removeDuplicateLocationUpdates to set
     */
    public void setRemoveDuplicateLocationUpdates(boolean removeDuplicateLocationUpdates) {
        this.removeDuplicateLocationUpdates = removeDuplicateLocationUpdates;
    }

    /**
     * @return the minSequenceLength
     */
    public int getMinSequenceLength() {
        return minSequenceLength;
    }

    /**
     * @param minSequenceLength the minSequenceLength to set
     */
    public void setMinSequenceLength(int minSequenceLength) {
        this.minSequenceLength = minSequenceLength;
    }

    /**
     * @return the executeQuery
     */
    public boolean isExecuteQuery() {
        return executeQuery;
    }

    /**
     * @param executeQuery the executeQuery to set
     */
    public void setExecuteQuery(boolean executeQuery) {
        this.executeQuery = executeQuery;
    }
    
    
}
