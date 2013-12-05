/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicModel;

/**
 *
 * @author Rashmika
 */
public class BasicModel {
    
    /**
     * Calculate the current traffic level
     * @param towerData
     * @return 
     */
    public int ForecastTrafficLevel_Current(String towerData){
        int trafficLevel = 2;
        String[] tempData = towerData.split("#");
        double userCount = Double.parseDouble(tempData[4]);
        
        trafficLevel = this.toTrafficLevel(userCount);
        
        return trafficLevel;
    }
    
    /**
     * * Predict the traffic level for 10 minutes ahead
     * @param towerData
     * @return 
     */
    public int ForecastTrafficLevel_10Min(String towerData){
        int trafficLevel = 2;
        String[] tempData = towerData.split("#");
        String timeZone = tempData[2]+"#"+tempData[3];
        double userCount = Double.parseDouble(tempData[4]);
        
        trafficLevel = this.toTrafficLevel_10Min(timeZone, userCount);
        
        return trafficLevel;
    }
    
    /**
     * Current Traffic Prediction Model
     * @param num1
     * @return 
     */
    private static int toTrafficLevel(double num1){
        int trafficLevel = 2;        
        if(num1<=0.2){
            trafficLevel = 1;            
        }else if(num1<=0.45){
            trafficLevel = 2;
        }else if(num1<=0.7){
            trafficLevel = 3;
        }else{
            trafficLevel = 4;
        }       
        return trafficLevel;
    }
    
    private static int toTrafficLevel_10Min(String timeZone, double num1){
        
        int trafficLevel = 2;
        
        if(num1<=0.2){
            trafficLevel = 1;            
        }else if(num1<=0.45){
            trafficLevel = 2;
        }else if(num1<=0.7){
            trafficLevel = 3;
        }else{
            trafficLevel = 4;
        }         
        
        int status = 0;
        switch(timeZone){
            case "06:00:00#06:10:00" : status = 0; break;
            case "06:10:00#06:20:00" : status = 0; break;
            case "06:20:00#06:30:00" : status = 0; break;
            case "06:30:00#06:40:00" : status = 0; break;
            case "06:40:00#06:50:00" : status = 1; break;
            case "06:50:00#07:00:00" : status = 1; break;

            case "07:00:00#07:10:00" : status = 0; break;
            case "07:10:00#07:20:00" : status = 1; break;
            case "07:20:00#07:30:00" : status = 1; break;
            case "07:30:00#07:40:00" : status = 0; break;
            case "07:40:00#07:50:00" : status = 0; break;
            case "07:50:00#08:00:00" : status = 0; break;

            case "08:00:00#08:10:00" : status = 1; break;
            case "08:10:00#08:20:00" : status =1; break;
            case "08:20:00#08:30:00" : status = 1; break;
            case "08:30:00#08:40:00" : status = -1; break;
            case "08:40:00#08:50:00" : status = -1; break;
            case "08:50:00#09:00:00" : status = 0; break;

            case "09:00:00#09:10:00" : status = -1; break;
            case "09:10:00#09:20:00" : status = -1; break;
            case "09:20:00#09:30:00" : status = -1; break;
            case "09:30:00#09:40:00" : status = 0; break;
            case "09:40:00#09:50:00" : status = 0; break;
            case "09:50:00#10:00:00" : status = 0; break;

            case "10:00:00#10:10:00" : status = 0; break;
            case "10:10:00#10:20:00" : status = 0; break;
            case "10:20:00#10:30:00" : status = 0; break;
            case "10:30:00#10:40:00" : status = 0; break;
            case "10:40:00#10:50:00" : status = 0; break;
            case "10:50:00#11:00:00" : status = 1; break;

            case "11:00:00#11:10:00" : status = 1; break;
            case "11:10:00#11:20:00" : status = 0; break;
            case "11:20:00#11:30:00" : status =0; break;
            case "11:30:00#11:40:00" : status = 0; break;
            case "11:40:00#11:50:00" : status = 0; break;
            case "11:50:00#12:00:00" : status = 1; break;

            case "12:00:00#12:10:00" : status = 1; break;
            case "12:10:00#12:20:00" : status = 1; break;
            case "12:20:00#12:30:00" : status = 0; break;
            case "12:30:00#12:40:00" : status = 1; break;
            case "12:40:00#12:50:00" : status = 0; break;
            case "12:50:00#13:00:00" : status = -1; break;

            case "13:00:00#13:10:00" : status = 0; break;
            case "13:10:00#13:20:00" : status = 1; break;
            case "13:20:00#13:30:00" : status = 1; break;
            case "13:30:00#13:40:00" : status = 0; break;
            case "13:40:00#13:50:00" : status = 0; break;
            case "13:50:00#14:00:00" : status = 1; break;

            case "14:00:00#14:10:00" : status = 1; break;
            case "14:10:00#14:20:00" : status = 0; break;
            case "14:20:00#14:30:00" : status = 0; break;
            case "14:30:00#14:40:00" : status = 0; break;
            case "14:40:00#14:50:00" : status = -1; break;
            case "14:50:00#15:00:00" : status = -1; break;

            case "15:00:00#15:10:00" : status = -1; break;
            case "15:10:00#15:20:00" : status = 0; break;
            case "15:20:00#15:30:00" : status = 0; break;
            case "15:30:00#15:40:00" : status = -1; break;
            case "15:40:00#15:50:00" : status = 0; break;
            case "15:50:00#16:00:00" : status = 0; break;
        }
        
        if(trafficLevel==1 && (status == 0 || status == 1)){
            trafficLevel += status;            
        }else if(trafficLevel==2 && (status == 0 || status == 1)){
            trafficLevel += status;
        }else if(trafficLevel==3){
            trafficLevel = 3;
        }else if(trafficLevel==4 && (status == 0 || status == -1)){
            trafficLevel += status;
        }     
        
        return trafficLevel;
    }
    
}
