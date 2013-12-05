/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InputRead;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rashmika
 */
public class InputReader {

    /**
     * Read CCTV Traffic Counts
     * @param csvFileName
     * @return 
     */
    public static Hashtable<String, Integer> readCCTVTrafficCount(String csvFileName){
        
        Hashtable<String, Integer> cctvData = new Hashtable<String, Integer>();
        
        try {
            String csv = csvFileName;
            CSVReader csvReader = new CSVReader(new FileReader(csv));
            String[] row = null;
            while((row = csvReader.readNext()) != null){
                cctvData.put((row[0]+ "#" + row[1]), Integer.parseInt(row[2]));                
            }        
            csvReader.close();
        } catch (IOException ex) {
            Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cctvData;
    }
    
    /**
     * Read Location Update counts
     * @param csvFileName
     * @return 
     */
    public static Hashtable<String, Integer> readMobitelUserCount(String csvFileName){
        
        Hashtable<String, Integer> mobitelData = new Hashtable<String, Integer>();
        
        try {
            String csv = csvFileName;
            CSVReader csvReader = new CSVReader(new FileReader(csv));
            String[] row = null;
            while((row = csvReader.readNext()) != null){
                mobitelData.put((row[0]+ "#" + row[1] + "#" +row[2]), Integer.parseInt(row[3]));                
            }        
            csvReader.close();
        } catch (IOException ex) {
            Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mobitelData;
    }
    
}
