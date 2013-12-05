/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicEntity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Get the properties from the configuration file
 * @author Rashmika
 */
public class RoadPechProperties {
    
    private String username;
    private String password;
    private String dbname;
    private String dbusername;
    private String dbpassword;
    private String centralDBURL;
    private String mobitelSubscriberRowtablename;
    private String mobitelCellTowertablenaem;
    private String passKey;
    private String passValue;
    
    /**
     * At the construction time, read the app.config file and load its data to the application
     */
    public RoadPechProperties() {
        
        InputStream is = null;
        
        try {
            Properties prop = new Properties();
            String fileName = "app.config";
            is = new FileInputStream(fileName);
            prop.load(is);
            
            this.username =  prop.getProperty("app.username");
            this.password =  prop.getProperty("app.password");
            this.dbname = prop.getProperty("app.mobitelSubscriberDBName");
            //this.mobitelSubscribertablename = prop.getProperty("app.mobitelSubscribertableName");
            this.mobitelSubscriberRowtablename = prop.getProperty("app.mobitelSubscriberRowtableName");
            this.mobitelCellTowertablenaem = prop.getProperty("app.mobitelCellTowertableName");
            this.centralDBURL = prop.getProperty("app.centralDatabaseURL");
            this.passKey =  prop.getProperty("app.passKey");
            this.passValue =  prop.getProperty("app.passValue");
            this.dbusername =  prop.getProperty("app.mobitelSubscriberDBusername");
            this.dbpassword =  prop.getProperty("app.mobitelSubscriberDBPassword");
            
        } catch (Exception ex) {
            Logger.getLogger(RoadPechProperties.class.getName()).log(Level.SEVERE, null, ex);
            this.username = "cse";
            this.password = "admin";
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(RoadPechProperties.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the dbname
     */
    public String getDbname() {
        return dbname;
    }

    /**
     * @param dbname the dbname to set
     */
    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    /**
     * @return the mobitelCellTowertablenaem
     */
    public String getMobitelCellTowertablenaem() {
        return mobitelCellTowertablenaem;
    }

    /**
     * @return the centralDBURL
     */
    public String getCentralDBURL() {
        return centralDBURL;
    }

    /**
     * @return the passKey
     */
    public String getPassKey() {
        return passKey;
    }

    /**
     * @return the passValue
     */
    public String getPassValue() {
        return passValue;
    }

    /**
     * @return the dbusername
     */
    public String getDbusername() {
        return dbusername;
    }

    /**
     * @return the dbpassword
     */
    public String getDbpassword() {
        return dbpassword;
    }

    /**
     * @return the mobitelSubscriberRowtablename
     */
    public String getMobitelSubscriberRowtablename() {
        return mobitelSubscriberRowtablename;
    }
    
}
