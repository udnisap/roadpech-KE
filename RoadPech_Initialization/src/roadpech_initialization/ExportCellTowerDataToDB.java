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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rashmika
 */
public class ExportCellTowerDataToDB {
    
    public static void main(String[] args) {
        ExportCellTowerDataToDB test = new ExportCellTowerDataToDB();
        
        Hashtable<String,InCellData> in_mobitel_cell_tower_data = test.readMobitelCellDataFromFile("mobitel_in_data//Cell_ID.csv");
        
        String dbname = "roadpech_mobile";
        String tablename = "cell_tower_data";
        
        test.addData(dbname, tablename, in_mobitel_cell_tower_data);
        
        System.out.println("Completed importing relevant data to SQL!");
        
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
                        String cgi = sCurrentContent[0];
                        String angle = sCurrentContent[3];
                        String siteName = sCurrentContent[4];
                        String type = sCurrentContent[5];

                        cgi = convertDecimalToHex(cgi);

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
    
    private String convertDecimalToHex(String dec){
        String result = "";
        
        String[] temp = new String[3];
        temp[0] = dec.substring(0, 5);
        temp[1] = dec.substring(5, 10);
        temp[2] = dec.substring(10, 15);
        
        String hex1 = appendZeroToCGI(Integer.toHexString(Integer.parseInt(temp[1])));
        String hex2 = appendZeroToCGI(Integer.toHexString(Integer.parseInt(temp[2])));
        
        result = temp[0] + "-" + hex1 + "-" + hex2;
        
        return result;
    }
    
    private String appendZeroToCGI(String init){
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
    
    public boolean addData(String dbname, String tableName, Hashtable<String,InCellData> datalist){
        
        boolean sent = false;

        try {
            
            Connection connection = this.getConnection(dbname);
            
            String query = "INSERT INTO " + tableName + " (cgi, longVal, latVal, angle, sitename, type) values(?, ?, ?, ?, ?, ?)";
            
            //getting statement object
            PreparedStatement  pstmt = (PreparedStatement) connection.prepareStatement(query);
            
            int i=0;
            
            Set<String> keys = datalist.keySet();
            
            for(String datakey : keys){
                pstmt.setString(1, datalist.get(datakey).getCgi());
                pstmt.setString(2, datalist.get(datakey).getLongVal());
                pstmt.setString(3, datalist.get(datakey).getLatVal());
                pstmt.setString(4, datalist.get(datakey).getAngle());
                pstmt.setString(5, datalist.get(datakey).getSiteName());
                pstmt.setString(6, datalist.get(datakey).getType());

                pstmt.executeUpdate();
                if(i==10000){
                    break;
                }
                i++;
            }

            pstmt.close();
            connection.close();
            
            sent = true;

        } catch (SQLException ex) {
            Logger.getLogger(ExportCellTowerDataToDB.class.getName()).log(Level.SEVERE, null, ex);
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
