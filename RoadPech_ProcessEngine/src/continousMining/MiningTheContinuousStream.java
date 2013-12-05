package continousMining;

import ProcessEngine.LiveProcess;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import process.FilterByLocationUpdateCounts;
import process.FilterByLocationUpdateFreq;

import dbConnection.DBConnection;

/*
 * Author : Malinga Perera
 * Class  : MiningTheContinuousStream
 * 
 * Data is currently received from GSM provider as a bulk of historical data, those data is 
 * collected with 15-minute intervals. On the ideal scenario, we should get this as a continuous 
 * steam of data. Below model is created to use to represent design decisions and 
 * performance testing on the design. 
 */
public class MiningTheContinuousStream implements Runnable {
    private static Connection connect = null;
    private Statement statement = null;
    private Statement statement2 = null;
    private static String dataBaseName = "roadpech_mobile";
    private static String username = "root";
    private static String password = "";
    private static String tempDataTable = "dataset_cm_temp"; // temporary data
							     // table
    private static String filterTableName = "temp_filter";
    private static String dataTable = "dataset_cm";
    private static String outputable = "count_output"; // final output will be
						       // written to this table
    private static ResultSet resultSet = null;

    private boolean should_shutdown = false;
    
    public void run() {
        if(!should_shutdown){
            final DBConnection connectionCreater = new DBConnection();
            try {
                connect = connectionCreater.getConnection(dataBaseName, username, password);
                final MiningTheContinuousStream continuousMiner = new MiningTheContinuousStream();
                continuousMiner.pushToKnowledgeEngine();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public void shutdown(){
        this.should_shutdown = true;
    }
    
    public void restart(){
        this.should_shutdown = false;
    }

    /**
     * Data will arrive at a temporary table, which will be used for calculation
     * and will be truncated after adding to the records to the main data table.
     * @return
     * @throws SQLException
     */
    public ResultSet readTempTable() throws SQLException {
	final String query = "SELECT * FROM `" + dataBaseName + "`.`" + tempDataTable + "`;";
	System.out.println(query);
	statement = connect.createStatement();
	resultSet = statement.executeQuery(query);
	return resultSet;
    }

    public void emptyTempTable() throws SQLException {
	final String query = "TRUNCATE TABLE " + tempDataTable + ";";
	System.out.println(query);
	statement = connect.createStatement();
	statement.execute(query);
    }
    
    public void emptyFilterTable() throws SQLException {
	final String query = "TRUNCATE TABLE " + filterTableName + ";";
	System.out.println(query);
	statement = connect.createStatement();
	statement.execute(query);
    }

    public void insertToMainTable(ResultSet resultSet) throws SQLException {
	while (resultSet.next()) {
	    final String query = "INSERT INTO `" + dataBaseName + "`.`" + dataTable
		    + "`(`imsi`,`date`,`time`,`cgi`)\r\nVALUES('" + resultSet.getString("imsi") + "', '"
		    + resultSet.getString("date") + "', '" + resultSet.getString("time") + "', '"
		    + resultSet.getString("cgi") + "');";
	    // System.out.println(query);
	    statement2 = connect.createStatement();
	    statement2.execute(query);
	}
    }


    /**
     * For the records within last 10 minutes, we have to calculate the user
     * count per cgi, which can be done by a simple group by query with �cgi�.
     * This user counts will be stored in a separate table, all the cgi�s will
     * have a count per each 10 minutes making time and cgi the primary key for
     * this table.
     * @throws SQLException
     */
    public void updateCountTable() throws SQLException {
	readTempTable();
	insertToMainTable(resultSet);
	emptyTempTable();
	final String query = "INSERT INTO `" + dataBaseName + "`.`" + outputable + "` (`" + outputable + "`.`cgi`, `"
		+ outputable + "`.`longitude`, `" + outputable + "`.`latitude`,`" + outputable + "`.`angle`,  `"
		+ outputable
		+ "`.`count`)\r\nSELECT t1.`cgi`, t2.`longVal`, t2.`latVal`, t2.`angle`,  COUNT(*)\r\nFROM `"
		+ dataBaseName + "`.`" + dataTable + "` As t1\r\nLEFT JOIN `" + dataBaseName
		+ "`.`cell_tower_data` as t2\r\nON t1.`cgi`=t2.`cgi`\r\nGROUP BY t2.`cgi`";
	statement.execute(query);
    }

    /**
     * This method will push filtered data into the knowledge engine
     * @throws Exception
     */
    public void pushToKnowledgeEngine() throws Exception {
	readTempTable();
	final String query = filterTable();

	System.out.println(query);
	resultSet = statement.executeQuery(query);
	String filteredDataSet[] = new String[4];

	int count = 0;
	Date dNow = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("HH:mm:00");

	Calendar cal = Calendar.getInstance();
	cal.setTime(dNow);
	cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)/10 *10);
	String curruntTime = ft.format(cal.getTime());
	cal.add(Calendar.MINUTE, -10);
	String beforeTime = ft.format(cal.getTime());

	System.out.println("Current Date: " + ft.format(dNow));
	int maxCount = Integer.MIN_VALUE, minCount = Integer.MAX_VALUE;
	while (resultSet.next()) {
	    int userCount = Integer.parseInt(resultSet.getString("count"));
	    if (userCount > maxCount) {
		maxCount = userCount;
	    }

	    if (userCount < minCount) {
		minCount = userCount;
	    }
	}
	resultSet.beforeFirst();
	while (resultSet.next()) {
	    String cgi = resultSet.getString("cgi");
	    // <code> <number of tower> <long-lat-angle>
	    // 1-Danister-De-Silva 1 79.8775-6.91679-85 (41301-afc8-0e99)
	    // 2-Gnanadasa-Predeepa-NW 1 79.8765-6.91605-250 (41301-afc8-0dbd)
	    // 4-Gnanadasa-Predeepa-EW 1 79.881132-6.913941-210
	    // (41301-afc8-0fc0)
	    // 5-Elvitigala 1 79.877082-6.909195-80 (41301-afc8-081c)
	    if (cgi.equalsIgnoreCase("41301-afc8-0e99")) {
		// "79.881132-6.913941-210#06:00:00#06:30:00#Count"
		filteredDataSet[count] = resultSet.getString("longVal") + "-" + resultSet.getString("latVal") + "-"
			+ resultSet.getString("angle") + "#" + beforeTime + "#" + curruntTime + "#"
			+ normalize(resultSet.getString("count"), minCount, maxCount);
		count++;
	    }
	    if (cgi.equalsIgnoreCase("41301-afc8-0dbd")) {
		filteredDataSet[count] = resultSet.getString("longVal") + "-" + resultSet.getString("latVal") + "-"
			+ resultSet.getString("angle") + "#" + beforeTime + "#" + curruntTime + "#"
			+ normalize(resultSet.getString("count"), minCount, maxCount);
		count++;
	    }
	    if (cgi.equalsIgnoreCase("41301-afc8-0fc0")) {
		filteredDataSet[count] = resultSet.getString("longVal") + "-" + resultSet.getString("latVal") + "-"
			+ resultSet.getString("angle") + "#" + beforeTime + "#" + curruntTime + "#"
			+ normalize(resultSet.getString("count"), minCount, maxCount);
		count++;
	    }
	    if (cgi.equalsIgnoreCase("41301-afc8-081c")) {
		filteredDataSet[count] = resultSet.getString("longVal") + "-" + resultSet.getString("latVal") + "-"
			+ resultSet.getString("angle") + "#" + beforeTime + "#" + curruntTime + "#"
			+ normalize(resultSet.getString("count"), minCount, maxCount);
		count++;
	    }

	}

	LiveProcess liveProcess = new LiveProcess();
	liveProcess.process(filteredDataSet);
	emptyTempTable();
	emptyFilterTable();
    }


    /**
     * This method will do all data filtering work
     * @return
     * @throws Exception
     */
    private String filterTable() throws Exception {
	

	FilterByLocationUpdateCounts filter = new FilterByLocationUpdateCounts(filterTableName, tempDataTable,
		"to_be_filtered", 2, 100);
	filter.setEditExistingTable(false);
	filter.filterByCounts();

	FilterByLocationUpdateFreq filter2 = new FilterByLocationUpdateFreq(true, filterTableName, filterTableName,
		"to_be_filtered", "00:15:00");
	filter2.filterByFreq();

	// insertToMainTable(resultSet);
	final String query = "SELECT t1.`cgi`, t2.`longVal`, t2.`latVal`, t2.`angle`,  COUNT(*) as count\r\nFROM `"
		+ dataBaseName + "`.`" + filterTableName + "` As t1\r\nLEFT JOIN `" + dataBaseName
		+ "`.`cell_tower_data` as t2\r\nON t1.`cgi`=t2.`cgi`\r\nGROUP BY t2.`cgi`";
	return query;
    }

    /**
     * This method will remove the records older than 1 hour
     * 
     * @param beforeTime
     * @throws SQLException
     */
    public void removeOldData(Date dNow) throws SQLException {
	Calendar cal = Calendar.getInstance();
	cal.setTime(dNow);
	cal.add(Calendar.HOUR, -1);
	SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
	String beforeTime = ft.format(cal.getTime());

	final String query = "DELETE\r\nFROM `roadpech_mobile`.`dataset`\r\nWHERE `dataset`.`time` < '" + beforeTime
		+ "'";
	System.out.println(query);
	statement = connect.createStatement();
	statement.execute(query);
    }

    /**
     * This method will normalize data to 0 to 1 range
     * 
     * @param userCount
     * @param min
     * @param max
     * @return
     */
    public String normalize(String userCount, int min, int max) {
	float number = Integer.parseInt(userCount);
	return Float.toString(((number - min) / (max - min)));

    }
}
