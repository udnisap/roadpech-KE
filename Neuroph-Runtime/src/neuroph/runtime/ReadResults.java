/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuroph.runtime;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author Rashmika
 */
public class ReadResults {
    
    public Hashtable<String, Integer> read(String fileName){
        String csvFile = fileName;
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
        Hashtable<String, Integer> results = new Hashtable<>();
        String[] temp;
        
	try { 
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                temp = line.split(cvsSplitBy);
                results.put(temp[0], Integer.parseInt(temp[2]));
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
        
        return results;
    }
    
}
