package process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbConnection.DBConnection;

/*
 * Author : Malinga Perera
 * Class  : MineSequencedData
 * 
 * This class is used for Mine location sequences of user created by the sequence data class.
 * Here we will use the user sequences to identify count of sequence appearances with in each 15 
 * minutes.    
 */
public class MineSequencedData {

    private static Connection connect = null;
    private Statement statement = null;
    private static String dataBaseName = "roadpech_mobile";			// Name of the database
    private static String username = "root";					// UserName of the above database
    private static String password = "";					// Password of the above database
    private static String sequencedTable = "dataset_filtered_date_new11";	// Sequence data table (Where the sequenced data is)
    private ResultSet resultSet = null;

    public static void main(String[] args) throws Exception {
	final DBConnection connectionCreater = new DBConnection();
	connect = connectionCreater.getConnection(dataBaseName, username, password);
	final MineSequencedData sequencer = new MineSequencedData();
	sequencer.countPer15Miniute("41301-afc8-0e9a, 41301-afc8-081c");
    }

    /*
     * This method will return sequence counts per each hour for a given sequence
     * Sequence should be given as a string input
     */
    public void countPerHour(String cgi) throws SQLException {
	final String query = "SELECT hour(`" + sequencedTable
		+ "`.`start_time`) as hour, count(*) as count \r\nFROM `" + dataBaseName + "`.`"
		+ sequencedTable + "`\r\nWHERE `v`.`cgi` LIKE '%" + cgi
		+ "%'\r\nGROUP BY hour(`" + sequencedTable + "`.`start_time`);";
	System.out.println(query);
	statement = connect.createStatement();
	resultSet = statement.executeQuery(query);
	writeResultSet(resultSet);
    }

    /*
     * This method will return sequence counts per each 15 minutes for a given sequence
     * Sequence should be given as a string input
     */
    public void countPer15Miniute(String cgi) throws SQLException {
	final String query = "SELECT hour(`" + sequencedTable + "`.`start_time`) as hour, minute(`" 
                + sequencedTable + "`.`start_time`) as minute, \r\nCASE \r\n WHEN minute(start_time) BETWEEN 0 and 14 THEN '00'\r\n WHEN minute(start_time) BETWEEN 15 and 29 THEN '15'\r\nWHEN minute(start_time) BETWEEN 30 and 44 THEN '30'\r\nWHEN minute(start_time) BETWEEN 45 and 59 THEN '45'\r\nEND AS intervals,\r\ncount(*) as count\r\nFROM `roadpech_mobile`.`" 
                + sequencedTable + "`\r\nWHERE `" + sequencedTable + "`.`cgi` LIKE '%"+cgi+"%'\r\nGROUP BY hour(`" + sequencedTable 
                + "`.`start_time`), intervals;\r\n";
	System.out.println(query);
	statement = connect.createStatement();
	resultSet = statement.executeQuery(query);
	writeResultSet(resultSet);
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
	while (resultSet.next()) {
	    System.out.println(resultSet.getString("hour") + ":" + resultSet.getString("minute")
		    + " =" + resultSet.getString("count"));
	}
    }

}
