/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicEntity;

/**
 *
 * @author Rashmika
 */
public class TrafficLevel {
    private int low = 1;
    private int mid = 2;
    private int high = 3;

    /**
     * @return the low
     */
    public int getLow() {
        return low;
    }

    /**
     * @return the mid
     */
    public int getMid() {
        return mid;
    }

    /**
     * @return the high
     */
    public int getHigh() {
        return high;
    }
    
    public int getDefault(){
        return mid;
    }
}
