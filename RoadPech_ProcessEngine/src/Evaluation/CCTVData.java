/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation;


/**
 *
 * @author Rashmika
 */
public class CCTVData {
    private String road;
    private int date;
    private String timeInterval;
    private int trafficValue;

    public CCTVData(String road, int date, String timeInterval, int trafficValue) {
        this.road = road;
        this.date = date;
        this.timeInterval = timeInterval;
        this.trafficValue = trafficValue;
    }

    /**
     * @return the road
     */
    public String getRoad() {
        return road;
    }

    /**
     * @return the date
     */
    public int getDate() {
        return date;
    }

    /**
     * @return the timeInterval
     */
    public String getTimeInterval() {
        return timeInterval;
    }

    /**
     * @return the trafficValue
     */
    public int getTrafficValue() {
        return trafficValue;
    }
    
}
