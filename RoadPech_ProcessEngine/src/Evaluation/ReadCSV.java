/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author Rashmika
 */
public class ReadCSV {
   
    /**
     * Read actual traffic data.
     * @return 
     */
    public static Hashtable<String,CCTVData> read(String filename){
        
        Hashtable<String,CCTVData> cctvDataTable = new Hashtable<>();
        
        String csvFile = filename;
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
        String[] segment;
        String key;
        
	try {
 
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                segment = line.split(cvsSplitBy);
                //road#date#timeinterval
                key = segment[0]+"#"+segment[1]+"#"+segment[2];
                cctvDataTable.put(key, new CCTVData(segment[0], Integer.parseInt(segment[1]), 
                        segment[2],Integer.parseInt(segment[3])));
            }
 
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
            if (br != null) {
                try {
                        br.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
            }
	}
 
	return cctvDataTable;
    }
    
}
