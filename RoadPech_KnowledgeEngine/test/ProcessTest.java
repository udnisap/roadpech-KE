/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import BasicEntity.Road;
import BasicEntity.RoadPechProperties;
import BasicEntity.SampleDate;
import DBFacade.Facade_In_Subscriber_Mobitel;
import DBFacade.ReadRoadRelatedTowers;
import MediationLayer.IN_Data_Transform;
import java.util.Hashtable;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Rashmika
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({})
public class ProcessTest extends TestCase{
    
    public void testGetConstrainedDataCount() {
        RoadPechProperties prop = new RoadPechProperties();
        String inDataBase = prop.getDbname();
        String inCellTowerTableName = prop.getMobitelCellTowertablenaem();
        String insubscriberRowTableName = prop.getMobitelSubscriberRowtablename();
        
        Facade_In_Subscriber_Mobitel test = new Facade_In_Subscriber_Mobitel();
        int count = test.getConstrainedDataCount(inDataBase, inCellTowerTableName, 
                insubscriberRowTableName, "09:00:00", "10:00:00", "79.881132", 
                "6.913941", "210", SampleDate.D11);
        
        Assert.assertEquals(6569, count);
    }
    
    public void testGetRoadRelatedTowers() {
        Hashtable<String, Road> table = null;
        IN_Data_Transform in_data_transformer = new IN_Data_Transform();
        
        table = in_data_transformer.getRoadRelatedTowers();
        Assert.assertNotNull(table);
    }
    
    public void testReadRoadTowers() {
        String[] roadTowers = null; 
        roadTowers = ReadRoadRelatedTowers.readRoadTowers();
        
        Assert.assertNotNull(roadTowers);
    }
    
}