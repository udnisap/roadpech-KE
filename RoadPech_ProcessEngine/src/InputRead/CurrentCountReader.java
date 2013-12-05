/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InputRead;

import BasicEntity.SampleDate;
import BasicEntity.Road;
import BasicEntity.RoadPechProperties;
import BasicEntity.TimeZones;
import BasicEntity.Tower;
import DBFacade.Facade_In_Subscriber_Mobitel;
import DBFacade.ReadRoadRelatedTowers;
import ThreadPoolPkg.WorkerThread;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Rashmika
 */
public class CurrentCountReader {
    
    /**
     * Retrieve current Road Feed for evaluation purposes
     * @param dateVal
     * @return 
     */
    public static Hashtable<String, Road> GetCurrentFeedRoadTable(SampleDate dateVal){
        
        RoadPechProperties prop = new RoadPechProperties();
        String inDataBase = prop.getDbname();
        String inCellTowerTableName = prop.getMobitelCellTowertablenaem(); 
        String insubscriberRowTableName = prop.getMobitelSubscriberRowtablename();
        
        Hashtable<String, Road> roadTable = ReadRoadRelatedTowers.getRoadRelatedTowers();
        
        int timeZoneCount = TimeZones.getTimeZoneCount();
        String[] timezones = TimeZones.getTimeZones(); 
        
        Facade_In_Subscriber_Mobitel in_facade_feed = new Facade_In_Subscriber_Mobitel();
        
        String[] tempLocation, tempTimeZone;
        int mobileCount=0;
        Tower tower;
        for(String roadKey : roadTable.keySet()){           
            Hashtable<String, Tower> towers = roadTable.get(roadKey).getTowers();
            for(String towerKey : towers.keySet()){
                tempLocation = towerKey.split("-");
                tower = towers.get(towerKey);
                for(int i=0; i<timeZoneCount;i++){
                    tempTimeZone = timezones[i].split("#");
                    mobileCount = in_facade_feed.getConstrainedDataCount(inDataBase, 
                            inCellTowerTableName,insubscriberRowTableName, tempTimeZone[0], 
                            tempTimeZone[1], tempLocation[0], tempLocation[1], tempLocation[2], dateVal); 
                    tower.setMobileTrafficCount(i, mobileCount);
                }
            }            
        }
        
        return roadTable;
    }
    
    private static void StartThreadPool(Hashtable<String, Road> roadTable, 
            SampleDate dateVal, String[] timezones, int timeZoneCount){
        
        ExecutorService executor = Executors.newFixedThreadPool(roadTable.size());
        
        for(String roadKey : roadTable.keySet()){  
            Runnable worker = new WorkerThread(roadTable, dateVal, 
            timezones, timeZoneCount, roadKey);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }        
    }
    
}
