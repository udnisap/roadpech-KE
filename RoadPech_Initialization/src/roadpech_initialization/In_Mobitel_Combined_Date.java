/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roadpech_initialization;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

/**
 *
 * @author Rashmika
 */
public class In_Mobitel_Combined_Date {

    private String imsi;
    private Date date;
    private Time time;
    private String cgi;
    private String longVal;
    private String latVal;
    private String angle;
    private String siteName;
    private String type;
    private int day;

    public In_Mobitel_Combined_Date(String imsi, Date date, Time time, String cgi, String longVal, String latVal, String angle, String siteName, String type) {
        this.imsi = imsi;
        this.date = date;
        this.time = time;
        this.cgi = cgi;
        this.longVal = longVal;
        this.latVal = latVal;
        this.angle = angle;
        this.siteName = siteName;
        this.type = type;
    }
    
    private void calculateDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.day = cal.DAY_OF_WEEK;
    }

    /**
     * @return the imsi
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the time
     */
    public Time getTime() {
        return time;
    }

    /**
     * @return the cgi
     */
    public String getCgi() {
        return cgi;
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
     * @return the siteName
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the day
     */
    public int getDay() {
        return day;
    }


}
