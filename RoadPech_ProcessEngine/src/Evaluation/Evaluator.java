/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation;

import java.util.Hashtable;

/**
 *
 * @author Rashmika
 */
public class Evaluator {
    
    /**
     * 
     * @param date
     * @param trafficLevels - roadKey+"#"+towerKey+"#"+timezones[i], trafficLevel
     */
    public static StringBuilder evaluate(int date, int horizon, Hashtable<String, Integer> trafficLevels, StringBuilder output){
        String actualTrafficDataFileName = Evaluator.getFileName(horizon);
        Hashtable<String,CCTVData> cctvDataTable = ReadCSV.read(actualTrafficDataFileName);
        
        String[] temp;
        String cctvKey;
        int totalCount = 0;
        int match = 0;
        int misByOne = 0;
        for( String trafficLevelKey : trafficLevels.keySet() ){
            temp = trafficLevelKey.split("#");
            cctvKey = temp[0]+"#"+date+"#"+temp[2]+"#"+temp[3];
            if( trafficLevels.get(trafficLevelKey) == cctvDataTable.get(cctvKey).getTrafficValue() ){
                match++;
            }
            
            if( (trafficLevels.get(trafficLevelKey) - cctvDataTable.get(cctvKey).getTrafficValue() == 1 )
                    || (trafficLevels.get(trafficLevelKey) - cctvDataTable.get(cctvKey).getTrafficValue() == -1 ) ){
                misByOne++;
            }
            
            totalCount++;
        }
        
        output.append("Total Results: "+totalCount+"\r\n");
        output.append("Correct Predictions: "+(match+misByOne)+"\r\n");
        output.append("Correct Percentatge: "+ (( (double) (match+misByOne)/ (double) totalCount)*100) + "%" +"\r\n");
        
        return output;
    }
    
    /**
     * Construct the correct actual traffic value file name
     * @param horizon
     * @return 
     */
    private static String getFileName(int horizon){
        String filename = "";
        switch (horizon){
            case 0: filename = "Evaluation-CCTV-Files/CCTVData_0.csv";
                break;
            case 10: filename = "Evaluation-CCTV-Files/CCTVData_10.csv";
                break;
            case 20: filename = "Evaluation-CCTV-Files/CCTVData_20.csv";
                break;
            default:
        }
        return filename;
    }
    
}
