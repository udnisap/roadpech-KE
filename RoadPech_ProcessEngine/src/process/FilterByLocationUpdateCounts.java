package process;

import java.sql.SQLException;

import dbConnection.DBConnection;

/*
 * Author : Malinga Perera
 * Class  : FilterByLocationUpdateCounts
 * 
 * This class is used for filter users by the location update counts. We observed that some users have only one location update, 
 * which is insufficient to identify it as a vehicle or walking person. And we found some people with over 100 location updates 
 * within a single day, which meant either they were wondering in the same place over and over or they were handed over between 
 * several towers again and again. With some observations it is clear that 2nd case was the most possible. Because of the above 
 * Problems we decided to filter-out both low end and high end of the users by location updates. 
 */
public class FilterByLocationUpdateCounts extends Setup {
    public FilterByLocationUpdateCounts(String newTableName, String tableToFilter, String tempTableName,
	    int minCount, int maxCount) {
	super();
	this.newTableName = newTableName;
	this.tableToFilter = tableToFilter;
	this.tempTableName = tempTableName;
	this.minCount =  Integer.toString(minCount);
	this.maxCount = Integer.toString(maxCount);
    }
    
    public FilterByLocationUpdateCounts() {
	this("dataset_filtered_date_new16", "dataset_filtered_date16", "to_be_filtered", 2, 100);
    }

    /*
     * Filtering can be done on the same table or it is possible to create a new
     * table with filtered data. Below variable controls that aspect. Setting it
     * to true will do the filtering on the existing table and some records will
     * be deleted from it. If it is set to false, program will create a separate
     * table and add items that we want to keep.
     */
    private boolean editExistingTable = true;
   
    // if new table is created it will have this name
    private String newTableName;

    private String tableToFilter; 		// source
    // this table will be used to hold intermediate data
    private String tempTableName;
 // location updates with below this value will be filtered
    private String minCount; 
 // location updates with above this value will be filtered
    private String maxCount;
// debug = true will print queries
    private boolean debugEnabled = false;

    public static void main(String[] args) throws Exception {
	
	
    }

    /*
     * This method will filter the records based one the min and max count
     * values. This will use the below 2 methods in the process
     */
    public void filterByCounts() throws Exception {
	DBConnection connectionCreater = new DBConnection();
	connect = connectionCreater.getConnection(dataBaseName, username, password);
	this.createTempTable();
	if (editExistingTable) {
	    String query = "DELETE FROM `" + dataBaseName + "`.`" + tableToFilter + "`\r\nWHERE `"
		    + tableToFilter + "`.`imsi` IN (SELECT `imsi`\r\nFROM `" + dataBaseName + "`.`"
		    + tempTableName + "`);";
	    debugPrint(query,"Fltering data frome table");
	    statement = connect.createStatement();
	    statement.execute(query);
	} else {
	    
	    this.createNewTable(newTableName);
	    String query = "INSERT INTO `" + dataBaseName + "`.`" + newTableName
		    + "`\r\nSELECT *\r\nFROM `" + tableToFilter + "`\r\nWHERE `" + tableToFilter
		    + "`.`imsi` NOT IN (SELECT `imsi` FROM `" + dataBaseName + "`.`"
		    + tempTableName + "`);";
	    debugPrint(query,"Fltering data frome table");
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
	debugPrint(query,"temp tabloe dropped");
	statement = connect.createStatement();
	statement.execute(query);

	query = "CREATE TABLE IF NOT EXISTS `"
		+ tempTableName
		+ "` (\r\n  `imsi` varchar(20) NOT NULL,\r\nKEY `imsi` (`imsi`)\r\n) ENGINE=InnoDB DEFAULT CHARSET=latin1;\r\n";
	debugPrint(query,"Created Temporary table");
	statement.execute(query);

	query = "INSERT INTO `" + dataBaseName + "`.`" + tempTableName + "`\r\nSELECT `"
		+ tableToFilter + "`.`imsi`\r\nFROM `" + dataBaseName + "`.`" + tableToFilter
		+ "`\r\nGROUP BY `" + tableToFilter + "`.`imsi`\r\nHAVING COUNT(`" + tableToFilter
		+ "`.`imsi`)<" + minCount + " OR COUNT(`" + tableToFilter + "`.`imsi`)>" + maxCount
		+ ";";
	debugPrint(query,"Inserting data into temp table");
	statement.execute(query);
	
	
    }

    /*
     * This method will be used to create a new table add the new data
     */
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
     * @return the editExistingTable
     */
    public boolean isEditExistingTable() {
        return editExistingTable;
    }

    /**
     * @param editExistingTable the editExistingTable to set
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
     * @return the tempTableName
     */
    public String getTempTableName() {
        return tempTableName;
    }

    /**
     * @param tempTableName the tempTableName to set
     */
    public void setTempTableName(String tempTableName) {
	this.tempTableName = tempTableName;
    }

    /**
     * @return the minCount
     */
    public String getMinCount() {
        return minCount;
    }

    /**
     * @param minCount the minCount to set
     */
    public void setMinCount(String minCount) {
	this.minCount = minCount;
    }

    /**
     * @return the maxCount
     */
    public String getMaxCount() {
        return maxCount;
    }

    /**
     * @param maxCount the maxCount to set
     */
    public void setMaxCount(String maxCount) {
	this.maxCount = maxCount;
    }

}
