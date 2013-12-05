/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MediationLayer;

import DBFacade.Facade_In_Subscriber_Mobitel;
import BasicEntity.Road;
import BasicEntity.RoadPechProperties;
import BasicEntity.SampleDate;
import DBFacade.ReadRoadRelatedTowers;
import java.util.Hashtable;

/**
 *
 * @author Rashmika
 */
public class IN_Data_Transform {
    
    private Facade_In_Subscriber_Mobitel in_facade;
    private String inDataBase;
    private String inCellTowerTableName;
    private String insubscriberRowTableName;
    
    /**
     * Initiate transform layer
     */
    public IN_Data_Transform(){
        in_facade = new Facade_In_Subscriber_Mobitel();
        RoadPechProperties prop = new RoadPechProperties();
        this.inDataBase = prop.getDbname();
        this.inCellTowerTableName = prop.getMobitelCellTowertablenaem();
        this.insubscriberRowTableName = prop.getMobitelSubscriberRowtablename();
    }
    
    /**
     * Roads to process
     * @return 
     */
    public Hashtable<String, Road> getRoadRelatedTowers(){
        String[] roadTowers = ReadRoadRelatedTowers.readRoadTowers();
        Hashtable<String, Road> roadsTable = new Hashtable<String, Road>(roadTowers.length);
        String[] temp;        
        for(int i=0; i<roadTowers.length; i++){
            temp = roadTowers[i].split(" ");
            switch (Integer.parseInt(temp[1])){
                case 1 :
                    roadsTable.put(temp[0], new Road(temp[0], 1, temp[2])); break;
                case 2 : 
                    roadsTable.put(temp[0], new Road(temp[0], 2, (temp[2]+" "+ temp[3]))); break;
                default:
                    break;
            }           
        }
        return roadsTable;
    }
    
    /**
     * 
     * @param from
     * @param to
     * @param longVal
     * @param latVal
     * @param angle
     * @param dateVal
     * @return int location update count
     */
    public int getConstrainedInDataCount(String from, String to, String longVal, String latVal, String angle, SampleDate dateVal){
        return in_facade.getConstrainedDataCount(this.inDataBase, this.inCellTowerTableName, this.insubscriberRowTableName, from, to, longVal, latVal, angle, dateVal);
    }
    
}
