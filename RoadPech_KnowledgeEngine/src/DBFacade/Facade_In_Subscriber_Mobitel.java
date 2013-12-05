/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBFacade;

import BasicEntity.IN_Mobitel;
import BasicEntity.IN_Mobitel_Cell_Tower_Data;
import BasicEntity.SampleDate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Work to integrate all the database communication 
 * from the application to the Database
 *
 * @author Rashmika
 */
public class Facade_In_Subscriber_Mobitel {
    
    /**
     * Get the number of location updates from the database
     * @param dbname
     * @param celltowertableName
     * @param mobitelTablename
     * @param from
     * @param to
     * @param longVal
     * @param latVal
     * @param angle
     * @param dateVal
     * @return int count
     */
    public int getConstrainedDataCount(String dbname, String celltowertableName, String mobitelTablename, 
            String from, String to, String longVal, String latVal, String angle, SampleDate dateVal){

        Connection connection = this.getConnection(dbname);
        
        int dataLines = 0;

        try{
            
            String query = "";
            
            //Retrieve data for required date
            if(SampleDate.D11==dateVal){
                query = "SELECT COUNT("+mobitelTablename+".imsi) AS totalcount \n" +
                            "FROM "+mobitelTablename+" JOIN cell_tower_data\n" +
                            "WHERE "+mobitelTablename+".cgi=cell_tower_data.cgi \n" +
                            "	AND ("+mobitelTablename+".date>'2013-06-11')  \n" +
                            "	AND ("+mobitelTablename+".time>'"+from+"')  \n" +
                            "	AND ("+mobitelTablename+".time<'"+to+"') \n" +
                            "	AND ("+celltowertableName+".longVal = '"+longVal+"')\n" +
                            "	AND ("+celltowertableName+".latVal = '"+latVal+"')" +
                            "	AND ("+celltowertableName+".angle = '"+angle+"')" ;
            }else if(SampleDate.D12==dateVal){
                query = "SELECT COUNT("+mobitelTablename+".imsi) AS totalcount \n" +
                            "FROM "+mobitelTablename+" JOIN cell_tower_data\n" +
                            "WHERE "+mobitelTablename+".cgi=cell_tower_data.cgi \n" +
                            "	AND ("+mobitelTablename+".date>'2013-06-12')  \n" +
                            "	AND ("+mobitelTablename+".time>'"+from+"')  \n" +
                            "	AND ("+mobitelTablename+".time<'"+to+"') \n" +
                            "	AND ("+celltowertableName+".longVal = '"+longVal+"')\n" +
                            "	AND ("+celltowertableName+".latVal = '"+latVal+"')" +
                            "	AND ("+celltowertableName+".angle = '"+angle+"')" ;
            }else if(SampleDate.D14==dateVal){
                query = "SELECT COUNT("+mobitelTablename+".imsi) AS totalcount \n" +
                            "FROM "+mobitelTablename+" JOIN cell_tower_data\n" +
                            "WHERE "+mobitelTablename+".cgi=cell_tower_data.cgi \n" +
                            "	AND ("+mobitelTablename+".date>'2013-06-14')  \n" +
                            "	AND ("+mobitelTablename+".time>'"+from+"')  \n" +
                            "	AND ("+mobitelTablename+".time<'"+to+"') \n" +
                            "	AND ("+celltowertableName+".longVal = '"+longVal+"')\n" +
                            "	AND ("+celltowertableName+".latVal = '"+latVal+"')" +
                            "	AND ("+celltowertableName+".angle = '"+angle+"')" ;
            }

            //getting statement object
            Statement pstmt = connection.createStatement();
            ResultSet rs = pstmt.executeQuery(query);
            rs.next();
            dataLines = rs.getInt("totalcount");

            pstmt.close();
            connection.close();

        } catch(SQLException ex){
            Logger.getLogger(Facade_In_Subscriber_Mobitel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dataLines;

    }
     
    /**
     * To get all the mails in the given table
     * @param dbname
     * @param tableName
     * @return ArrayList<IN_Mobitel_Cell_Tower_Data>
     */
    public ArrayList<IN_Mobitel_Cell_Tower_Data> getCellTowerData(String dbname, String tableName){

        Connection connection = this.getConnection(dbname);
        
        ArrayList<IN_Mobitel_Cell_Tower_Data> dataLines = new ArrayList<IN_Mobitel_Cell_Tower_Data>();

        try{
            
            String query = "SELECT * from "+tableName;

            //getting statement object
            PreparedStatement  pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){

                IN_Mobitel_Cell_Tower_Data data = new IN_Mobitel_Cell_Tower_Data();

                data.setCgi(rs.getString(2));
                data.setLongVal(rs.getString(3));
                data.setLatVal(rs.getString(4));
                data.setAngle(rs.getString(5));
                data.setSiteName(rs.getString(6));
                data.setType(rs.getString(7));
                
                dataLines.add(data);

            }

            pstmt.close();
            connection.close();

        } catch(SQLException ex){
            Logger.getLogger(Facade_In_Subscriber_Mobitel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dataLines;

    }
    
    /**
     * Method to get the database connection
     * @param dbName
     * @return 
     */
    private Connection getConnection(String dbName){
        
        try {
            //Setting up the connection
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/" + dbName;
            Connection connection = (Connection) DriverManager.getConnection( url,"root", "");
            return connection;
        } catch (SQLException ex) {
            Logger.getLogger(Facade_In_Subscriber_Mobitel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Facade_In_Subscriber_Mobitel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }
    
}
