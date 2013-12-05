/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicEntity;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

/**
 *
 * @author Rashmika
 */
public class IN_Mobitel {
    
    private String imsi;
    private Date date;
    private Time time;
    private String cgi;
    private String longVal;
    private String latVal;
    private String angle;
    private String siteName;
    private String type;    

    /**
     * @return the imsi
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * @param imsi the imsi to set
     */
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the time
     */
    public Time getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * @return the cgi
     */
    public String getCgi() {
        return cgi;
    }

    /**
     * @param cgi the cgi to set
     */
    public void setCgi(String cgi) {
        this.cgi = cgi;
    }

    /**
     * @return the longVal
     */
    public String getLongVal() {
        return longVal;
    }

    /**
     * @param longVal the longVal to set
     */
    public void setLongVal(String longVal) {
        this.longVal = longVal;
    }

    /**
     * @return the latVal
     */
    public String getLatVal() {
        return latVal;
    }

    /**
     * @param latVal the latVal to set
     */
    public void setLatVal(String latVal) {
        this.latVal = latVal;
    }

    /**
     * @return the angle
     */
    public String getAngle() {
        return angle;
    }

    /**
     * @param angle the angle to set
     */
    public void setAngle(String angle) {
        this.angle = angle;
    }

    /**
     * @return the siteName
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * @param siteName the siteName to set
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    public int getDayofWeek(){
        Calendar calender = Calendar.getInstance();
        calender.setTime(this.date);
        
        return calender.get(Calendar.DAY_OF_WEEK);   
    }
    
}
