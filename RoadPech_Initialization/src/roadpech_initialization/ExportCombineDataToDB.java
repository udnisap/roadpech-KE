/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roadpech_initialization;

import BasicEntity.GeneralUtil;
import DBFacade.Facade_In_Subscriber_Mobitel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rashmika
 */
public class ExportCombineDataToDB {
    
    public static void main(String[] args) {
        ExportCombineDataToDB exporter = new ExportCombineDataToDB();
        
        //Get location update data
        ArrayList<InMobitelSubscriberData> in_mobitel_subscriber_data = exporter.readMobitelSubscriberFromDatabase();
        //Get cell tower data
        Hashtable<String,InCellData> in_mobitel_cell_tower_data = exporter.readMobitelCellDataFromFile("mobitel_in_data//Cell_ID.csv");
        ArrayList<In_Mobitel_Combined_Date> combined_data = new ArrayList<In_Mobitel_Combined_Date>();

        //Combine both location update data and cell tower data
        for(InMobitelSubscriberData subscriber_data : in_mobitel_subscriber_data){
            
            if(!in_mobitel_cell_tower_data.containsKey(subscriber_data.getCgi())){
                continue;
            }else{
                InCellData cellData = in_mobitel_cell_tower_data.get(subscriber_data.getCgi());
            
                combined_data.add(new In_Mobitel_Combined_Date(subscriber_data.getImsi(), subscriber_data.getDate(),
                        subscriber_data.getTime(), subscriber_data.getCgi(),
                         cellData.getLongVal(), cellData.getLatVal(), cellData.getAngle(), cellData.getSiteName(), cellData.getType()));
            }           
            
        }
        
        System.out.println("Number of Initial Data Points: " + in_mobitel_subscriber_data.size() );
        System.out.println("Number of Cell Tower Data Points: " + in_mobitel_cell_tower_data.size() );
        System.out.println("Number of Combined Data Points: " + combined_data.size() );
        
        String dbname = "roadpech_data";
        String tablename = "in_mobitel_combine_data";
                
        exporter.addData(dbname, tablename, combined_data);
        
        System.out.println("Database (Unique imsi) Data: "+exporter.getDistinctDataCount(dbname, tablename));
   
        
    }
  
    public int getDistinctDataCount(String dbname, String tableName){

        Connection connection = this.getConnection(dbname);
        
        int dataLines = 0;

        try{
            
            String query = "SELECT COUNT(DISTINCT imsi) AS totalcount from "+tableName;

            //getting statement object
            Statement  pstmt = connection.createStatement();
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
    
    public Hashtable<String,InCellData> readMobitelCellDataFromFile(String filename){
        BufferedReader br = null;
        String splitBy = "\\,";
        Hashtable<String,InCellData> indata = new Hashtable<String, InCellData>();
 
        try {

                String sCurrentLine;
                String[] sCurrentContent;

                br = new BufferedReader(new FileReader(filename));

                while ((sCurrentLine = br.readLine()) != null) {
                    sCurrentContent = sCurrentLine.split(splitBy);                    
                    
                    String longVal = sCurrentContent[1];
                    String latVal = sCurrentContent[2];
                    
                    if(GeneralUtil.isValid(latVal, longVal)){                  
                        String cgi = convertDecimalToHex(sCurrentContent[0]); 
                        String angle = sCurrentContent[3];
                        String siteName = sCurrentContent[4];
                        String type = sCurrentContent[5];

                        indata.put(cgi, new InCellData(cgi, longVal, latVal, angle, siteName, type));
                    }
                }

        } catch (IOException e) {
                e.printStackTrace();
        } finally {
                try {
                        if (br != null)br.close();
                } catch (IOException ex) {
                        ex.printStackTrace();
                }
                return indata;
        }
    }
    
    public ArrayList<InMobitelSubscriberData> readMobitelSubscriberFromFile(String filename){
        BufferedReader br = null;
        ArrayList<InMobitelSubscriberData> indata = new ArrayList<InMobitelSubscriberData>();
 
        try {

                String sCurrentLine;
                String[] sCurrentContent;
                
                String imsi;
                Date date;
                String[] temp_date;
                Time time;
                String[] temp_time;
                String cgi;

                br = new BufferedReader(new FileReader(filename));

                while ((sCurrentLine = br.readLine()) != null) {
                    sCurrentContent = sCurrentLine.split("\\|");
                    imsi = sCurrentContent[0];
                    
                    temp_date = sCurrentContent[1].split("\\-");
                    int year = Integer.parseInt(temp_date[0])-1900;
                    int month = Integer.parseInt(temp_date[1]);
                    int day = Integer.parseInt(temp_date[2]);                    
                    date = new Date(year,month,day);
                    
                    temp_time = sCurrentContent[2].split("\\:");
                    int hour = Integer.parseInt(temp_time[0]);
                    int min = Integer.parseInt(temp_time[1]);
                    time = new Time(hour, min, 00);
                    
                    //System.out.println(""+time.toString());
                    
                    cgi = sCurrentContent[3];//this.convertHexaToDecimal(sCurrentContent[3]);
                    
                    indata.add(new InMobitelSubscriberData(imsi, date, time, cgi));
                }

        } catch (IOException e) {
                e.printStackTrace();
        } finally {
                try {
                        if (br != null)br.close();
                } catch (IOException ex) {
                        ex.printStackTrace();
                }
                return indata;
        }
    }
    
    public ArrayList<InMobitelSubscriberData> readMobitelSubscriberFromDatabase(){

        String dbname = "roadpech_data";
        String tableName = "dataset_filtered";
        ArrayList<InMobitelSubscriberData> indata = new ArrayList<InMobitelSubscriberData>();

        String imsi;
        Date date;
        String[] temp_date;
        Time time;
        String[] temp_time;
        String cgi;
        
        Connection connection = this.getConnection(dbname);

        try{
            
            String query = "SELECT * from "+tableName;

            //getting statement object
            PreparedStatement  pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){

                imsi = rs.getString(1);
                
                temp_date = rs.getString(2).split("\\-");
                int year = Integer.parseInt(temp_date[0])-1900;
                int month = Integer.parseInt(temp_date[1]);
                int day = Integer.parseInt(temp_date[2]);                    
                date = new Date(year,month,day);

                temp_time = rs.getString(3).split("\\:");
                int hour = Integer.parseInt(temp_time[0]);
                int min = Integer.parseInt(temp_time[1]);
                time = new Time(hour, min, 00);
                
                cgi = rs.getString(4);
                
                InMobitelSubscriberData data = new InMobitelSubscriberData(imsi, date, time, cgi);
                indata.add(data);

            }

            pstmt.close();
            connection.close();

        } catch(SQLException ex){
            Logger.getLogger(Facade_In_Subscriber_Mobitel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return indata;
    }
    
    private String convertDecimalToHex(String dec){
        String result = "";
        
        /**
         * "hamburger".substring(4, 8) returns "urge"
 "smiles".substring(1, 5) returns "mile"
         */
        
        String[] temp = new String[3];
        temp[0] = dec.substring(0, 5);
        temp[1] = dec.substring(5, 10);
        temp[2] = dec.substring(10, 15);
        
        String hex1 = appendZeroToCGI2(Integer.toHexString(Integer.parseInt(temp[1])));
        String hex2 = appendZeroToCGI2(Integer.toHexString(Integer.parseInt(temp[2])));
        
        result = temp[0] + "-" + hex1 + "-" + hex2;
        
        return result;
    }
    
    private String appendZeroToCGI2(String init){
        String result = "00000";
        
        if(init.length() == 4){
            result = init;
        }else if(init.length() == 3){
            result = "0"+init;
        }else if(init.length() == 2){
            result = "00"+init;
        }else if(init.length() == 1){
            result = "000"+init;
        }else if(init.length() == 0){
            result = "0000"+init;
        }
        
        return result;
    }
    
    private String convertHexaToDecimal(String hexa){
        
        String[] temparr = hexa.split("-");
        Long lpart1 = Long.parseLong(temparr[1], 16);
        String spart1 = appendZeroToCGI(Long.toString(lpart1));
        Long lpart2 = Long.parseLong(temparr[2], 16);
        String spart2 = appendZeroToCGI(Long.toString(lpart2));
        
        return temparr[0] + "" + spart1 + "" + spart2;
    }
    
    private String appendZeroToCGI(String init){
        String result = "00000";
        
        if(init.length() == 5){
            result = init;
        }else if(init.length() == 4){
            result = "0"+init;
        }else if(init.length() == 3){
            result = "00"+init;
        }else if(init.length() == 2){
            result = "000"+init;
        }else if(init.length() == 1){
            result = "0000"+init;
        }else if(init.length() == 0){
            result = "00000"+init;
        }
        
        return result;
    }
    
    public boolean addData(String dbname, String tableName, ArrayList<In_Mobitel_Combined_Date> datalist){
        
        boolean sent = false;

        try {
            
            Connection connection = this.getConnection(dbname);
            
            String query = "INSERT INTO " + tableName + " (imsi, date, time, cgi, longVal, latVal, angle, sitename, type) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            //getting statement object
            PreparedStatement  pstmt = (PreparedStatement) connection.prepareStatement(query);
            
            int i=0;
            
            for(In_Mobitel_Combined_Date data : datalist){
                pstmt.setString(1, data.getImsi());
                pstmt.setDate(2, data.getDate());
                pstmt.setTime(3, data.getTime());
                pstmt.setString(4, data.getCgi());
                pstmt.setString(5, data.getLongVal());
                pstmt.setString(6, data.getLatVal());
                pstmt.setString(7, data.getAngle());
                pstmt.setString(8, data.getSiteName());
                pstmt.setString(9, data.getType());

                pstmt.executeUpdate();
                if(i==15000){
                    break;
                }
                i++;
            }

            pstmt.close();
            connection.close();
            
            sent = true;

        } catch (SQLException ex) {
            Logger.getLogger(ExportCombineDataToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sent;

    }
    
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
