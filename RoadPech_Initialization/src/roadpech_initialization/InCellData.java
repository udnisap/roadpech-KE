/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roadpech_initialization;

/**
 *
 * @author Rashmika
 */
public class InCellData {
    
    private String cgi;
    private String longVal;
    private String latVal;
    private String angle;
    private String siteName;
    private String type;

    public InCellData(String cgi, String longVal, String latVal, String angle, String siteName, String type) {
        this.cgi = cgi;
        this.longVal = longVal;
        this.latVal = latVal;
        this.angle = angle;
        this.siteName = siteName;
        this.type = type;
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
    
}
