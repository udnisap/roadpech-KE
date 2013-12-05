/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roadpech_initialization;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Rashmika
 */
public class InMobitelSubscriberData {
    
    private String imsi;
    private Date date;
    private Time time;
    private String cgi;

    public InMobitelSubscriberData(String imsi, Date date, Time time, String cgi) {
        this.imsi = imsi;
        this.date = date;
        this.time = time;
        this.cgi = cgi;
    }

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
    
}
