/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicEntity;

import java.sql.Time;

/**
 * This will carry processd information of cell towers
 * @author Rashmika
 */
public class OUT_General {
    
    private String longVal;
    private String latVal;
    private String angle;
    private Time from;
    private Time to;
    private int dayCat; 
    private int userCount;   
    private int trafficLevel;

    /**
     * Constructor
     * @param longVal
     * @param latVal
     * @param angle
     * @param from
     * @param to
     * @param daycat
     * @param userCount
     * @param trafficLevel 
     */
    public OUT_General(String longVal, String latVal, String angle, String from, String to, int daycat, int userCount, int trafficLevel) {
        this.longVal = longVal;
        this.latVal = latVal;
        this.angle = angle;
        
        String splitBy = "\\:";
        String[] temp = from.split(splitBy);
        
        this.from = new Time( Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
        
        temp = to.split(splitBy);
        this.to = new Time( Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
        this.dayCat = daycat;
        this.userCount = userCount;
        this.trafficLevel = trafficLevel;
    }

    /**
     * @return the longVal
     */
    public String getLongVal() {
        return longVal;
    }

    /**
     * @return the latVal
     */
    public String getLatVal() {
        return latVal;
    }

    /**
     * @return the angle
     */
    public String getAngle() {
        return angle;
    }

    /**
     * @return the day
     */
    public int getDayCatagory() {
        return dayCat;
    }

    /**
     * @return the userCount
     */
    public int getUserCount() {
        return userCount;
    }

    /**
     * @return the from
     */
    public Time getFrom() {
        return from;
    }

    /**
     * @return the to
     */
    public Time getTo() {
        return to;
    }

    /**
     * @return the trafficLevel
     */
    public int getTrafficLevel() {
        return trafficLevel;
    }

}
