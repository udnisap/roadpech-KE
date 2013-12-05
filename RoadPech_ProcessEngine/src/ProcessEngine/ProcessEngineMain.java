/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcessEngine;

import BasicEntity.Road;
import BasicEntity.SampleDate;
import BasicEntity.TimeZones;
import BasicEntity.Tower;
import BasicEntity.TrafficHorizons;
import Evaluation.Evaluator;
import InputRead.CurrentCountReader;
import OutputWrite.Output_Traffic_Levels;
import java.util.Hashtable;
import neuroph.runtime.NeurophRuntime;
import roadpech_neuralnetwork.RoadPech_NeuralNetwork;

/**
 *
 * @author Rashmika
 */
public class ProcessEngineMain {
    
    public static void main(String[] args) {
        
        SampleDate dateVal = SampleDate.D14;
        int idate = 14;
        long start_time;
        long end_time;
        double difference1, difference2;
        StringBuilder display;
    
        //Evaluate
        System.out.println("\n----Basic-------"+idate+" 0----------\n");
        start_time = System.nanoTime();
        display = new StringBuilder("");
        Hashtable<String, Integer> trafficLevels_0 = ProcessEngineMain.ProcessFeedOnbasicModelwithTime(dateVal, display, TrafficHorizons.getHorizonOfCurrent());
        display = new StringBuilder("");
        end_time = System.nanoTime();
        difference1 = (end_time - start_time)/1e9;
        display = Evaluator.evaluate(idate,0, trafficLevels_0, display);  
        end_time = System.nanoTime();
        difference2 = (end_time - start_time)/1e9;
        System.out.println(""+display.toString());
        System.out.println("Process Time - "+difference1);
        System.out.println("Evaluate Time - "+difference2);
        
        System.out.println("\n-----Basic------"+idate+" 10----------\n");
        display = new StringBuilder("");
        start_time = System.nanoTime();
        Hashtable<String, Integer> trafficLevels_10 = ProcessEngineMain.ProcessFeedOnbasicModelwithTime(dateVal, display, TrafficHorizons.getHorizonOf10());
        display = new StringBuilder("");
        end_time = System.nanoTime();
        difference1 = (end_time - start_time)/1e9;
        display = Evaluator.evaluate(idate,10, trafficLevels_10, display);
        end_time = System.nanoTime();
        difference2 = (end_time - start_time)/1e9;
        System.out.println(""+display.toString());
        System.out.println("Process Time - "+difference1);
        System.out.println("Evaluate Time - "+difference2);
        
        System.out.println("\n-----Basic------"+idate+" 20----------\n");
        display = new StringBuilder("");
        start_time = System.nanoTime();
        Hashtable<String, Integer> trafficLevels_20 = ProcessEngineMain.ProcessFeedOnbasicModelwithTime(dateVal, display, TrafficHorizons.getHorizonOf20());
        display = new StringBuilder("");
        end_time = System.nanoTime();
        difference1 = (end_time - start_time)/1e9;
        display = Evaluator.evaluate(idate,20, trafficLevels_20, display); 
        end_time = System.nanoTime();
        difference2 = (end_time - start_time)/1e9;
        System.out.println(""+display.toString());
        System.out.println("Process Time - "+difference1);
        System.out.println("Evaluate Time - "+difference2);
        
        System.out.println("\n----NN-------"+idate+" 0----------\n");
        display = new StringBuilder("");
        start_time = System.nanoTime();
        trafficLevels_0 = ProcessEngineMain.ProcessFeedOnNeuralNetwork(dateVal, display, TrafficHorizons.getHorizonOfCurrent());
        display = new StringBuilder("");
        end_time = System.nanoTime();
        difference1 = (end_time - start_time)/1e9;
        display = Evaluator.evaluate(idate,0, trafficLevels_0, display);
        end_time = System.nanoTime();
        difference2 = (end_time - start_time)/1e9;
        System.out.println(""+display.toString());
        System.out.println("Process Time - "+difference1);
        System.out.println("Evaluate Time - "+difference2);
        
        System.out.println("\n-----NN------"+idate+" 10----------\n");
        display = new StringBuilder("");
        start_time = System.nanoTime();
        trafficLevels_10 = ProcessEngineMain.ProcessFeedOnNeuralNetwork(dateVal, display, TrafficHorizons.getHorizonOf10());
        display = new StringBuilder("");
        end_time = System.nanoTime();
        difference1 = (end_time - start_time)/1e9;
        display = Evaluator.evaluate(idate,10, trafficLevels_10, display);
        end_time = System.nanoTime();
        difference2 = (end_time - start_time)/1e9;
        System.out.println(""+display.toString());
        System.out.println("Process Time - "+difference1);
        System.out.println("Evaluate Time - "+difference2);
        
        System.out.println("\n-----NN------"+idate+" 20----------\n");
        display = new StringBuilder("");
        start_time = System.nanoTime();
        trafficLevels_20 = ProcessEngineMain.ProcessFeedOnNeuralNetwork(dateVal, display, TrafficHorizons.getHorizonOf20());
        display = new StringBuilder("");
        end_time = System.nanoTime();
        difference1 = (end_time - start_time)/1e9;
        display = Evaluator.evaluate(idate,20, trafficLevels_20, display); 
        end_time = System.nanoTime();
        difference2 = (end_time - start_time)/1e9;
        System.out.println(""+display.toString());
        System.out.println("Process Time - "+difference1);
        System.out.println("Evaluate Time - "+difference2);

    }
    
    public void update(){
        SampleDate dateVal = SampleDate.D14;        
        StringBuilder display = new StringBuilder("");
        
        Hashtable<String, Integer> trafficLevels_0 = ProcessEngineMain.ProcessFeedOnbasicModelwithTime(dateVal, display, TrafficHorizons.getHorizonOfCurrent());
        Hashtable<String, Integer> trafficLevels_10 = ProcessEngineMain.ProcessFeedOnbasicModelwithTime(dateVal, display, TrafficHorizons.getHorizonOf10());
        Hashtable<String, Integer> trafficLevels_20 = ProcessEngineMain.ProcessFeedOnbasicModelwithTime(dateVal, display, TrafficHorizons.getHorizonOf20());
        
        Output_Traffic_Levels.output(trafficLevels_0, trafficLevels_10, trafficLevels_20);
    }
    
    /**
     * Prediction Switch
     * @param date
     * @param model
     * @return 
     */
    public String predict(String date, String model){
        StringBuilder output = new StringBuilder("");
        SampleDate sDate;
        
        long start_time, end_time;
        double difference;
        
        start_time = System.nanoTime();
        
        switch(date){
            case "11th Tuesday": sDate = SampleDate.D11; break;
            case "12th Wednesday": sDate = SampleDate.D12; break;
            case "14th Friday": sDate = SampleDate.D14; break;
            default: sDate = SampleDate.D11;
        }
        
        switch(model){
                case "Basic Model Real Time": 
                    ProcessEngineMain.ProcessFeedOnbasicModelwithTime(sDate, output, TrafficHorizons.getHorizonOfCurrent());
                    break;
                case "Neural Network Real Time": 
                    ProcessEngineMain.ProcessFeedOnNeuralNetwork(sDate, output, TrafficHorizons.getHorizonOfCurrent());
                    break;
                case "Basic Model 10 Min": 
                    ProcessEngineMain.ProcessFeedOnbasicModelwithTime(sDate, output, TrafficHorizons.getHorizonOf10());
                    break;
                case "Neural Network 10 Min": 
                    ProcessEngineMain.ProcessFeedOnNeuralNetwork(sDate, output, TrafficHorizons.getHorizonOf10());
                    break;
                case "Basic Model 20 Min": 
                    ProcessEngineMain.ProcessFeedOnbasicModelwithTime(sDate, output, TrafficHorizons.getHorizonOf20());
                    break;
                case "Neural Network 20 Min": 
                    ProcessEngineMain.ProcessFeedOnNeuralNetwork(sDate, output, TrafficHorizons.getHorizonOf20());
                    break;                    
        }
        
        end_time = System.nanoTime();
        
        //Calculate processing time in seconds
        difference = (end_time - start_time)/1e9;

        output.append("\r\n Procesing Time : "+difference+" seconds");
        return output.toString();
    }
    
    /**
     * Traffic prediction on basic model
     * @param dateVal
     * @param display
     * @param horizon
     * @return 
     */
    private static Hashtable<String, Integer> ProcessFeedOnbasicModelwithTime(SampleDate dateVal, StringBuilder display, int horizon){
        
        BasicModel.BasicModelwithTime basicModel = new BasicModel.BasicModelwithTime();
        
        //Road-Tower-TimeZone,TrafficLevel
        Hashtable<String, Integer> trafficLevels = new Hashtable<>();

        //Retrieve Live Traffic Count feed
        Hashtable<String, Road> roadTable = CurrentCountReader.GetCurrentFeedRoadTable(dateVal);
        
        //Normalize the Live Traffic Count - to be used for the neural network forecasting
        roadTable = GetNormalizedCurrentFeedRoadTable(roadTable);
        
        int timeZoneCount = TimeZones.getTimeZoneCount();
        String[] timezones = TimeZones.getTimeZones();
        int trafficLevel;
        Tower tempTower;
        
        for(String roadKey : roadTable.keySet()){           
            Hashtable<String, Tower> towers = roadTable.get(roadKey).getTowers();
            display.append("Road: "+roadKey+"\r\n");
            for(String towerKey : towers.keySet()){
                tempTower = towers.get(towerKey);
                display.append("    Tower: "+towerKey+"\r\n");
                for(int i=0; i<timeZoneCount;i++){ 
                    //4-Gnanadasa-Predeepa-EW#79.881132-6.913941-210#06:00:00#06:30:00#Count
                    trafficLevel = basicModel.ForecastTrafficLevel(roadKey+"#"
                            +towerKey+"#"+timezones[i]+"#"
                            +tempTower.getNormalizedtMobileTrafficCount(i), horizon);
                    trafficLevels.put(roadKey+"#"+towerKey+"#"+timezones[i], trafficLevel);
                    display.append("        "+timezones[i]+" - "
                            +tempTower.getNormalizedtMobileTrafficCount(i)+" | "
                            +trafficLevel+"\r\n");
               }
            }            
        }         
        return trafficLevels;        
    }
     
    /**
     * Traffic prediction on Neural Network Model
     * @param dateVal
     * @param display
     * @param horizon
     * @return 
     */
    private static Hashtable<String, Integer> ProcessFeedOnNeuralNetwork(SampleDate dateVal, StringBuilder display, int horizon){
        RoadPech_NeuralNetwork neuralNetwork = new RoadPech_NeuralNetwork();
        NeurophRuntime runtime = new NeurophRuntime();
        //Road-Tower-TimeZone,TrafficLevel
        Hashtable<String, Integer> trafficLevels = new Hashtable<>();

        //Retrieve Live Traffic Count feed
        Hashtable<String, Road> roadTable = CurrentCountReader.GetCurrentFeedRoadTable(dateVal);
        
        //Normalize the Live Traffic Count - to be used for the neural network forecasting
        roadTable = GetNormalizedCurrentFeedRoadTable(roadTable);
        
        int timeZoneCount = TimeZones.getTimeZoneCount();
        String[] timezones = TimeZones.getTimeZones();
        int trafficLevel;
        Tower tempTower;
        
        for(String roadKey : roadTable.keySet()){           
            Hashtable<String, Tower> towers = roadTable.get(roadKey).getTowers();
            display.append("Road: "+roadKey+"\r\n");
            for(String towerKey : towers.keySet()){
                tempTower = towers.get(towerKey);
                display.append("    Tower: "+towerKey+"\r\n");
                for(int i=0; i<timeZoneCount;i++){ 
                    //4-Gnanadasa-Predeepa-EW#79.881132-6.913941-210#06:00:00#06:30:00#Count
                    trafficLevel = neuralNetwork.ForecastTrafficLevel(runtime,roadKey+"#"+towerKey+"#"+timezones[i]+
                            "#"+tempTower.getNormalizedtMobileTrafficCount(i), horizon);
                    trafficLevels.put(roadKey+"#"+towerKey+"#"+timezones[i], trafficLevel);
                    display.append("        "+timezones[i]+" - "+tempTower.getNormalizedtMobileTrafficCount(i)
                            +" | "+trafficLevel+"\r\n");
               }
            }            
        }         
        return trafficLevels;        
    }
        
    /**
     * Min-Max Normalization for location update counts
     * @param roadTable
     * @return 
     */
    private static Hashtable<String, Road> GetNormalizedCurrentFeedRoadTable(Hashtable<String, Road> roadTable){        
        
        int timeZoneCount = TimeZones.getTimeZoneCount();
        String[] timezones = TimeZones.getTimeZones();
        
        int minTrafficCount = Integer.MAX_VALUE ;
        int maxTrafficCount = Integer.MIN_VALUE ;  
        
        Hashtable<String, Tower> towers;
        Tower tempTower;
        
        double normalizedTrafficCount;
        
        //Min max should be calculated per each road segment
        for(String roadKey : roadTable.keySet()){           
            towers = roadTable.get(roadKey).getTowers();            
            //Parse 1 - Retrieve min and max traffic values
            for(String towerKey : towers.keySet()){
                tempTower = towers.get(towerKey);
                for(int i=0; i<timeZoneCount;i++){                    
                    if(tempTower.getMobileTrafficCount(i) < minTrafficCount && tempTower.getMobileTrafficCount(i) > 0){
                        minTrafficCount = tempTower.getMobileTrafficCount(i);
                    }
                    if(tempTower.getMobileTrafficCount(i) > maxTrafficCount){
                        maxTrafficCount = tempTower.getMobileTrafficCount(i);
                    }
                }//End of a tower for all its timezones
            } //End of a road segment of parse 1
            
            //Parse 2 - Normalize
            for(String towerKey : towers.keySet()){
                tempTower = towers.get(towerKey);
                for(int i=0; i<timeZoneCount;i++){   
                    normalizedTrafficCount = (double) (tempTower.getMobileTrafficCount(i)-minTrafficCount) / (double) (maxTrafficCount-minTrafficCount);
                    tempTower.setNormalizedMobileTrafficCount(i, normalizedTrafficCount);
                }//End of a tower for all its timezones
            }  //End of a road segment of parse 2
            
            minTrafficCount = Integer.MAX_VALUE ;
            maxTrafficCount = Integer.MIN_VALUE ; 
          
        }// End of all the road segments   

        return roadTable;
    }
    
    public String evaluate(String date, String model){
        StringBuilder output = new StringBuilder("");
        Hashtable<String, Integer> trafficLevels = null;
        SampleDate sDate;
        int idate = 11;
        int iHorizon = 0;

        switch(date){
            case "11th Tuesday": sDate = SampleDate.D11; idate=11; break;
            case "12th Wednesday": sDate = SampleDate.D12; idate=12; break;
            case "14th Friday": sDate = SampleDate.D14; idate=14; break;
            default: sDate = SampleDate.D11;
        }
        
        switch(model){
                case "Basic Model Real Time": 
                    trafficLevels = ProcessEngineMain.ProcessFeedOnbasicModelwithTime(sDate, output, TrafficHorizons.getHorizonOfCurrent());
                    iHorizon = TrafficHorizons.getHorizonOfCurrent();
                    break;
                case "Neural Network Real Time": 
                    trafficLevels = ProcessEngineMain.ProcessFeedOnNeuralNetwork(sDate, output, TrafficHorizons.getHorizonOfCurrent());
                    iHorizon = TrafficHorizons.getHorizonOfCurrent();
                    break;
                case "Basic Model 10 Min": 
                    trafficLevels = ProcessEngineMain.ProcessFeedOnbasicModelwithTime(sDate, output, TrafficHorizons.getHorizonOf10());
                    iHorizon = TrafficHorizons.getHorizonOf10();
                    break;
                case "Neural Network 10 Min": 
                    trafficLevels = ProcessEngineMain.ProcessFeedOnNeuralNetwork(sDate, output, TrafficHorizons.getHorizonOf10());
                    iHorizon = TrafficHorizons.getHorizonOf10();
                    break;
                case "Basic Model 20 Min": 
                    trafficLevels = ProcessEngineMain.ProcessFeedOnbasicModelwithTime(sDate, output, TrafficHorizons.getHorizonOf20());
                    iHorizon = TrafficHorizons.getHorizonOf20();
                    break;
                case "Neural Network 20 Min": 
                    trafficLevels = ProcessEngineMain.ProcessFeedOnNeuralNetwork(sDate, output, TrafficHorizons.getHorizonOf20());
                    iHorizon = TrafficHorizons.getHorizonOf20();
                    break;                    
        }
        
        output = new StringBuilder("");
        output = Evaluator.evaluate(idate, iHorizon, trafficLevels, output);
        
        return output.toString();
    }
    
}
