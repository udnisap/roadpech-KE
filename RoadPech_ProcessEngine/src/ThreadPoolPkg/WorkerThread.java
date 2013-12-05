/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadPoolPkg;

import BasicEntity.Road;
import BasicEntity.RoadPechProperties;
import BasicEntity.SampleDate;
import BasicEntity.Tower;
import DBFacade.Facade_In_Subscriber_Mobitel;
import java.util.Hashtable;

/**
 *
 * @author Rashmika
 */
public class WorkerThread implements Runnable{
    
    Hashtable<String, Road> roadTable;
    SampleDate dateVal;
    String[] timezones;
    int timeZoneCount;
    String roadKey;

    public WorkerThread(Hashtable<String, Road> roadTable, SampleDate dateVal, 
            String[] timezones, int timeZoneCount, String roadKey) {

        this.dateVal = dateVal;
        this.timezones = timezones;
        this.timeZoneCount = timeZoneCount;
        this.roadKey = roadKey;
        this.roadTable = roadTable;
    }

    @Override
    public void run() {
        
        String[] tempLocation, tempTimeZone;
        int mobileCount=0;
        Tower tower;
        Facade_In_Subscriber_Mobitel in_facade_feed = new Facade_In_Subscriber_Mobitel();
        RoadPechProperties prop = new RoadPechProperties();
        String inDataBase = prop.getDbname();
        String inCellTowerTableName = prop.getMobitelCellTowertablenaem(); 
        String insubscriberRowTableName = prop.getMobitelSubscriberRowtablename();
        
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

}
