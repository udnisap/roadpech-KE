/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OutputWrite;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rashmika
 */
public class Output_Traffic_Levels {

    public static void main(String[] args) {
        Output_Traffic_Levels.test();
    }
    
    public static void test(){
        Process_HttpUtil httpUtil = new Process_HttpUtil();

            Map<String, String> map = new HashMap<String, String>();
            map.put("road", "road"); 
            map.put("time", "time");
            map.put("level_0", "level_0");
            map.put("level_5", "level_5");
            map.put("level_10", "level_10");
            map.put("level_15", "level_15");
            map.put("level_20", "level_20");
            
            String result = "";
            try {
                result = httpUtil.doPost("http://roadpech.ideawide.com/api/index.php/prediction/", map);
            } catch (Exception ex) {
                Logger.getLogger(Process_HttpUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("UPdate sent to api - "+result);      
          
    }
    
    /**
     * 
     * @param trafficLevels 
     * Key - roadKey+"#"+towerKey+"#"+timezone
     * Value - trafficLevel
     */
    public static void output(Hashtable<String, Integer> trafficLevels_0, 
            Hashtable<String, Integer> trafficLevels_10, 
            Hashtable<String, Integer> trafficLevels_20){
        
        Process_HttpUtil httpUtil = new Process_HttpUtil();
        
        String[] temp;
        for(String key : trafficLevels_0.keySet()){
            temp = key.split("#");
            Map<String, String> map = new HashMap<String, String>();
            map.put("road", temp[0]); 
            map.put("time", temp[2]+"#"+temp[3]);
            map.put("level_0", Integer.toString(trafficLevels_0.get(key)));
            map.put("level_5", Double.toString(Math.ceil( (trafficLevels_0.get(key) + trafficLevels_10.get(key))/2)));
            map.put("level_10", Integer.toString(trafficLevels_10.get(key)));
            map.put("level_15", Double.toString(Math.ceil( (trafficLevels_0.get(key) + trafficLevels_10.get(key))/2)));
            map.put("level_20", Integer.toString(trafficLevels_20.get(key)));
            
            String result = "";
            try {
                result = httpUtil.doPost(" http://roadpech.ideawide.com/api/", map);
            } catch (Exception ex) {
                Logger.getLogger(Process_HttpUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
                   
        }       
        
    }
    
}
