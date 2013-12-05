/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicEntity;

/**
 *
 * @author Rashmika
 */
public class Tower {
    
    String name; //Name convention <long-lat-angle>
    int timeZonesCount;
    String[] timezones;
    int[] mobileUserTrafficCount;
    double[] normalizedMobileUserTrafficCount;
    
    /**
     * 
     * @param name 
     */
    public Tower(String name) {
        this.name = name;
        this.setTimeZones();
        mobileUserTrafficCount = new int[timeZonesCount];
        normalizedMobileUserTrafficCount = new double[timeZonesCount];
    }
    
    /**
     * 
     * @param i
     * @return 
     */
    public String getTimeZone(int i){
        return timezones[i];
    }
    
    public int getMobileTrafficCount(int timeZoneNumber){
        return mobileUserTrafficCount[timeZoneNumber];
    }
    
    public void setMobileTrafficCount(int timeZoneNumber, int trafficCount){
        mobileUserTrafficCount[timeZoneNumber] = trafficCount;
    }
    
    public double getNormalizedtMobileTrafficCount(int timeZoneNumber){
        return normalizedMobileUserTrafficCount[timeZoneNumber];
    }
    
    public void setNormalizedMobileTrafficCount(int timeZoneNumber, double trafficCount){
        normalizedMobileUserTrafficCount[timeZoneNumber] = trafficCount;
    }
    
    private void setTimeZones(){
        timezones = TimeZones.getTimeZones();
        timeZonesCount = TimeZones.zoneCount;
        
    }
    
}
