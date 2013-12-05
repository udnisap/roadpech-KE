/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package KnowledgeEngine;

import BasicEntity.Road;
import BasicEntity.SampleDate;
import BasicEntity.TimeZones;
import BasicEntity.Tower;
import MediationLayer.IN_Data_Transform;
import MediationLayer.OUT_Data_Transform;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * 
 * @author Rashmika
 */
public class EngineMain {
    
    private IN_Data_Transform in_data_transformer;
    private ArrayList<String> outputList;
    private Hashtable<String, Road> table;
    
    public EngineMain() {
        in_data_transformer = new IN_Data_Transform();
        outputList = new ArrayList<String>();
    }
    
    public static void main(String[] args) {
        EngineMain main = new EngineMain();
        main.Play(SampleDate.D11);
    }
    
    /**
     * Retrieve data for @dateVal date and
     * process the output location update counts
     * and create CSV file
     * @param dateVal
     * @return 
     */
    public boolean Play(SampleDate dateVal){             
        boolean result = false;       
        Process(dateVal);   
        result = WriteToCSV(dateVal);
        return result;
    }
    
    /**
     * Write output to the CSV file
     * @param dateVal
     * @return 
     */
    public boolean WriteToCSV(SampleDate dateVal){
        OUT_Data_Transform out_data_transformer = new OUT_Data_Transform();
        return out_data_transformer.writeCSV(this.outputList, dateVal);
    }
    
    /**
     * Process the location update counts
     * @param dateVal 
     */
    public void Process(SampleDate dateVal){
        
        //Get the roads to be consider in process
        table = in_data_transformer.getRoadRelatedTowers();       
        
        //Get time intervals
        int timeZoneCount = TimeZones.getTimeZoneCount();
        String[] timezones = TimeZones.getTimeZones();  
        
        String[] tempLocation, tempTimeZone;
        int mobileCount=0;
        Tower tower;
        /**
         * 1. Loop through each road and each tower
         * 2. Loop through each time interval
         * 3. Retrieve the location update count
         * 4. Set in the output data structure
         */
        for(String roadKey : table.keySet()){           
            Hashtable<String, Tower> towers = table.get(roadKey).getTowers();
            for(String towerKey : towers.keySet()){
                tempLocation = towerKey.split("-"); //<long-lat-angle>
                tower = towers.get(towerKey);
                for(int i=0; i<timeZoneCount;i++){
                    tempTimeZone = timezones[i].split("#");
                    mobileCount = in_data_transformer.getConstrainedInDataCount(tempTimeZone[0], tempTimeZone[1], tempLocation[0], tempLocation[1], tempLocation[2], dateVal);
                    outputList.add(roadKey+"$"+towerKey+"$"+timezones[i]+"$"+mobileCount);
                    tower.setMobileTrafficCount(i, mobileCount);
                }
            }            
        }
    }
   
}
