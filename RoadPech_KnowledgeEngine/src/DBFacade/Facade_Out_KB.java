/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBFacade;

import BasicEntity.SampleDate;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Work to integrate all the database communication 
 * from the application to the Database
 *
 * @author Rashmika
 */
public class Facade_Out_KB {
    
    /**
     * Write to CSV file
     * @param dataList
     * @param dateVal
     * @return status of writing
     */
    public boolean WirteCSV(ArrayList<String> dataList, SampleDate dateVal){
        
        String csv = "errornamed.csv";
        boolean result = false;
        
        if(SampleDate.D11==dateVal){
            csv = "mobitel_user_count_data_2013_06_11.csv";
        }else if(SampleDate.D12==dateVal){
            csv = "mobitel_user_count_data_2013_06_12.csv";
        }else if(SampleDate.D14==dateVal){
            csv = "mobitel_user_count_data_2013_06_14.csv";
        }
        
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            System.out.println("Lines "+dataList.size());
            for(String dataLine : dataList){
                String [] line = dataLine.split("\\$");
                writer.writeNext(line);
            }         
            writer.close();
            result = true;
        } catch (IOException ex) {
            Logger.getLogger(Facade_Out_KB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
}
