/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicEntity;

/**
 *
 * @author Rashmika
 */
public class TimeZones {
    
    public static int zoneCount = 60;
    
    /**
     *  Get the number of time intervals
     * @return int
     */
    public static int getTimeZoneCount(){
        return zoneCount;
    }
    
    /**
     *  Get the existing time intervals
     * @return String[]  
     */
    public static String[] getTimeZones(){
        
        String[] timezones = new String[zoneCount];
        
        timezones[0] = "06:00:00#06:10:00";
        timezones[1] = "06:10:00#06:20:00";
        timezones[2] = "06:20:00#06:30:00";
        timezones[3] = "06:30:00#06:40:00";
        timezones[4] = "06:40:00#06:50:00";
        timezones[5] = "06:50:00#07:00:00";
        
        timezones[6] = "07:00:00#07:10:00";
        timezones[7] = "07:10:00#07:20:00";
        timezones[8] = "07:20:00#07:30:00";
        timezones[9] = "07:30:00#07:40:00";
        timezones[10] = "07:40:00#07:50:00";
        timezones[11] = "07:50:00#08:00:00";
        
        timezones[12] = "08:00:00#08:10:00";
        timezones[13] = "08:10:00#08:20:00";
        timezones[14] = "08:20:00#08:30:00";
        timezones[15] = "08:30:00#08:40:00";
        timezones[16] = "08:40:00#08:50:00";
        timezones[17] = "08:50:00#09:00:00";
        
        timezones[18] = "09:00:00#09:10:00";
        timezones[19] = "09:10:00#09:20:00";
        timezones[20] = "09:20:00#09:30:00";
        timezones[21] = "09:30:00#09:40:00";
        timezones[22] = "09:40:00#09:50:00";
        timezones[23] = "09:50:00#10:00:00";
        
        timezones[24] = "10:00:00#10:10:00";
        timezones[25] = "10:10:00#10:20:00";
        timezones[26] = "10:20:00#10:30:00";
        timezones[27] = "10:30:00#10:40:00";
        timezones[28] = "10:40:00#10:50:00";
        timezones[29] = "10:50:00#11:00:00";
        
        timezones[30] = "11:00:00#11:10:00";
        timezones[31] = "11:10:00#11:20:00";
        timezones[32] = "11:20:00#11:30:00";
        timezones[33] = "11:30:00#11:40:00";
        timezones[34] = "11:40:00#11:50:00";
        timezones[35] = "11:50:00#12:00:00"; 
        
        timezones[36] = "12:00:00#12:10:00";
        timezones[37] = "12:10:00#12:20:00";
        timezones[38] = "12:20:00#12:30:00";
        timezones[39] = "12:30:00#12:40:00";
        timezones[40] = "12:40:00#12:50:00";
        timezones[41] = "12:50:00#13:00:00";
        
        timezones[42] = "13:00:00#13:10:00";
        timezones[43] = "13:10:00#13:20:00";
        timezones[44] = "13:20:00#13:30:00";
        timezones[45] = "13:30:00#13:40:00";
        timezones[46] = "13:40:00#13:50:00";
        timezones[47] = "13:50:00#14:00:00";
        
        timezones[48] = "14:00:00#14:10:00";
        timezones[49] = "14:10:00#14:20:00";
        timezones[50] = "14:20:00#14:30:00";
        timezones[51] = "14:30:00#14:40:00";
        timezones[52] = "14:40:00#14:50:00";
        timezones[53] = "14:50:00#15:00:00";
        
        timezones[54] = "15:00:00#15:10:00";
        timezones[55] = "15:10:00#15:20:00";
        timezones[56] = "15:20:00#15:30:00";
        timezones[57] = "15:30:00#15:40:00";
        timezones[58] = "15:40:00#15:50:00";
        timezones[59] = "15:50:00#16:00:00";
        
        return timezones;
    }
   
}