/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicEntity;

import java.util.Hashtable;

/**
 * The details of the road
 * @author Rashmika
 */
public class Road {
    
    private String name; //<1-Danister-De-Silva> <2-Gnanadasa-Predeepa-NW>
    private int numberOfTowers;
    private int trafficLevel;
    private Hashtable<String, Tower> towersTable;

    /**
     * 
     * @param name
     * @param towerCount
     * @param towers 
     */
    public Road(String name, int towerCount, String towers) {
        this.name = name;
        this.numberOfTowers = towerCount;
        this.towersTable = new Hashtable<String, Tower>();
        this.trafficLevel = -1;
        
        String[] temp = towers.split(" ");
        for(int i=0; i<towerCount; i++){
            this.towersTable.put(temp[i], new Tower(temp[i]) ) ; //<79.8765-6.91605-250>
        }
    }

    /**
     * Get the towers
     * @return 
     */
    public Hashtable<String, Tower> getTowers(){
        return towersTable;
    }
    
    /**
     * Update the towers
     * @param towersTable 
     */
    public void updateTowers(Hashtable<String, Tower> towersTable){
        this.towersTable = towersTable;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the numberOfTowers
     */
    public int getNumberOfTowers() {
        return numberOfTowers;
    }

    /**
     * @return the trafficLevel
     */
    public int getTrafficLevel() {
        return trafficLevel;
    }

    /**
     * @param trafficLevel the trafficLevel to set
     */
    public void setTrafficLevel(int trafficLevel) {
        this.trafficLevel = trafficLevel;
    }

    
    
}
