/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicEntity;

/**
 *
 * @author Rashmika
 */
public class GeneralUtil {
    
    public static boolean isValid(String lat, String lon){
        
        return ( (lat.equalsIgnoreCase("6.915083")&&lon.equalsIgnoreCase("79.877032"))
                || (lat.equalsIgnoreCase("6.909195")&&lon.equalsIgnoreCase("79.877082")) 
                || (lat.equalsIgnoreCase("6.91605")&&lon.equalsIgnoreCase("79.8765")) 
                || (lat.equalsIgnoreCase("6.91679")&&lon.equalsIgnoreCase("79.8775")) 
                || (lat.equalsIgnoreCase("6.913941")&&lon.equalsIgnoreCase("79.881132")) );
           
    }
}
