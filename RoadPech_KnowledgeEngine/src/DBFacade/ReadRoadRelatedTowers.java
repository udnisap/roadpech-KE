/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBFacade;

import BasicEntity.Road;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author Rashmika
 */
public class ReadRoadRelatedTowers {
    
    /**
     * Get the towers which are related to Roads
     * And their details
     * @return 
     */
    public static Hashtable<String, Road> getRoadRelatedTowers(){
        String[] roadTowers = ReadRoadRelatedTowers.readRoadTowers();
        Hashtable<String, Road> roadsTable = new Hashtable<String, Road>(roadTowers.length);
        String[] temp;        
        for(int i=0; i<roadTowers.length; i++){
            temp = roadTowers[i].split(" ");
            switch (Integer.parseInt(temp[1])){
                case 1 :
                    roadsTable.put(temp[0], new Road(temp[0], 1, temp[2])); break;
                case 2 : 
                    roadsTable.put(temp[0], new Road(temp[0], 2, (temp[2]+" "+ temp[3]))); break;
                default:
                    break;
            }           
        }
        return roadsTable;
    }
    
    public static String[] readRoadTowers(){
        BufferedReader br = null;
        String[] roadTowers = new String[1];
        try {
            boolean first = false;
            int numfiles = 0;
            String sCurrentLine;

            br = new BufferedReader(new FileReader("roadrelatedtowers.txt"));

            while ((sCurrentLine = br.readLine()) != null) {
                //Get the number of roads mentioned in the first line
                if(!first){
                    numfiles = Integer.parseInt(sCurrentLine);
                    roadTowers = new String[numfiles];
                    first=true;
                }  
                
                for(int i=0; i<numfiles; i++){
                    if ((sCurrentLine = br.readLine()) != null) {
                        roadTowers[i] = sCurrentLine;
                    }
                }
                
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return roadTowers;
        }
    }
    
}