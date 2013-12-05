/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcessEngine;

import BasicEntity.TrafficHorizons;
import OutputWrite.Output_Traffic_Levels;
import java.util.Hashtable;
import neuroph.runtime.NeurophRuntime;
import roadpech_neuralnetwork.RoadPech_NeuralNetwork;

/**
 *
 * @author Rashmika
 */
public class LiveProcess {
    
    /**
     * Sent live stream of updates
     * @param updates 
     */
    public void process(String[] updates){
        
        if(null == updates[0]){
            System.out.println("No Data Available");
            return;
        }
        
        System.out.println("In Process");
        for (String string : updates) {
            System.out.println(string);
        }
        
        String[] processedInputUpdates = this.completeRequest(updates);
        System.out.println("2");
        Hashtable<String, Integer> trafficLevels_0 = this.getPrediction(processedInputUpdates, TrafficHorizons.getHorizonOfCurrent());
        Hashtable<String, Integer> trafficLevels_10 = this.getPrediction(processedInputUpdates, TrafficHorizons.getHorizonOf10());
        Hashtable<String, Integer> trafficLevels_20 = this.getPrediction(processedInputUpdates, TrafficHorizons.getHorizonOf20());
    
        System.out.println("\n-------------Current---------------\n");
        for (String string : trafficLevels_0.keySet()) {
            System.out.println(string+" - "+trafficLevels_0.get(string));
        }
        System.out.println("\n-------------10 Min---------------\n");
        for (String string : trafficLevels_10.keySet()) {
            System.out.println(string+" - "+trafficLevels_10.get(string));
        }
        System.out.println("\n-------------20 Min---------------\n");
        for (String string : trafficLevels_20.keySet()) {
            System.out.println(string+" - "+trafficLevels_20.get(string));
        }
        
        Output_Traffic_Levels.output(trafficLevels_0, trafficLevels_10, trafficLevels_20);
    }
    
    /**
     * Connect to the NN and get predictions
     * @param processedInputUpdates
     * @param horizon
     * @return 
     */
    private Hashtable<String, Integer> getPrediction(String[] processedInputUpdates, int horizon){
        Hashtable<String, Integer> trafficLevels = new Hashtable<>();
        RoadPech_NeuralNetwork neuralNetwork = new RoadPech_NeuralNetwork();
        NeurophRuntime runtime = new NeurophRuntime();
        
        Integer trafficLevel;
        for(int i=0; i<processedInputUpdates.length; i++){
            trafficLevel= neuralNetwork.ForecastTrafficLevel(runtime, processedInputUpdates[i], horizon);
            trafficLevels.put(processedInputUpdates[i], trafficLevel);
        }    
        
        return trafficLevels;
    }
    
   /**
    * Complete the update request by adding missing location name
    * @param requests
    * @return 
    */
    private String[] completeRequest(String[] requests){
        String[] results = new String[requests.length];
        String[] temp;        
        for(int i=0; i<requests.length; i++){            
            temp = requests[i].split("#");
            results[i] = this.getLocationName(temp[0]) + "#" + requests[i];            
        }        
        return results;
    }
    
    /**
     * Get location name
     * @param location
     * @return 
     */
    private String getLocationName(String location){
        String result = "";        
        switch(location){            
            case "79.8775-6.91679-85" : result = "1-Danister-De-Silva"; break;
            case "79.8765-6.91605-250" : result = "2-Gnanadasa-Predeepa-NW"; break;
            case "79.881132-6.913941-210" : result = "4-Gnanadasa-Predeepa-EW"; break;
            case "79.877082-6.909195-80" : result = "5-Elvitigala"; break;            
        }        
        return result;
    }
    
}
