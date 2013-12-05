/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuroph.runtime;

import java.util.Hashtable;

/**
 *
 * @author Rashmika
 */
public class NeurophRuntime {

    Hashtable<String, Integer> one_0;
    Hashtable<String, Integer> one_10;
    Hashtable<String, Integer> one_20;
    Hashtable<String, Integer> two_0;
    Hashtable<String, Integer> two_10;
    Hashtable<String, Integer> two_20;
    Hashtable<String, Integer> three_0;
    Hashtable<String, Integer> three_10;
    Hashtable<String, Integer> three_20;
    Hashtable<String, Integer> four_0;
    Hashtable<String, Integer> four_10;
    Hashtable<String, Integer> four_20;
    ReadResults reader;
    
    public NeurophRuntime() {
        reader = new ReadResults();
        one_0 = reader.read("NN-Output-Files/1-Dannister-De-Silva-NN-0.csv");
        one_10 = reader.read("NN-Output-Files/1-Dannister-De-Silva-NN-10.csv");
        one_20 = reader.read("NN-Output-Files/1-Dannister-De-Silva-NN-20.csv");
        two_0 = reader.read("NN-Output-Files/2-Gnanadasa-Predeepa-NW-0.csv");
        two_10 = reader.read("NN-Output-Files/2-Gnanadasa-Predeepa-NW-10.csv");
        two_20 = reader.read("NN-Output-Files/2-Gnanadasa-Predeepa-NW-0.csv");
        three_0 = reader.read("NN-Output-Files/4-Gnanadasa-Predeepa-EW-20.csv");
        three_10 = reader.read("NN-Output-Files/4-Gnanadasa-Predeepa-EW-10.csv");
        three_20 = reader.read("NN-Output-Files/4-Gnanadasa-Predeepa-EW-20.csv");
        four_0 = reader.read("NN-Output-Files/5-Elvitigala-0.csv");
        four_10 = reader.read("NN-Output-Files/5-Elvitigala-10.csv");
        four_20 = reader.read("NN-Output-Files/5-Elvitigala-20.csv");
    }
    
    public void load(String fileName, Object object){
        
    }
    
    public int output(String filename, String timeZone){
       
        int result = 2;
        
        switch(filename){
            case "4-Gnanadasa-Predeepa-EW-NN-Train-with-11-12-0.nnet": 
                    result = (three_0.containsKey(timeZone)) ? three_0.get(timeZone) : 2 ;
                break;
            case "2-Gnanadasa-Predeepa-NW-NN-Trai-with-11-12-0.nnet": 
                    result = (two_0.containsKey(timeZone)) ? two_0.get(timeZone) : 2 ;
                break;
            case "5-Elvitigala-NN-11-12-0.nnet": 
                    result = (four_0.containsKey(timeZone)) ? four_0.get(timeZone) : 2 ;
                break;
            case "1-Danister-De-Silva-NN-Trai-with-11-12-0.nnet": 
                    result = (one_0.containsKey(timeZone)) ? one_0.get(timeZone) : 2 ;
                break;
            case "4-Gnanadasa-Predeepa-EW-NN-Train-with-11-12-10.nnet": 
                    result = (three_10.containsKey(timeZone)) ? three_10.get(timeZone) : 2 ;
                break;
            case "2-Gnanadasa-Predeepa-NW-NN-Trai-with-11-12-10.nnet": 
                    result = (two_10.containsKey(timeZone)) ? two_10.get(timeZone) : 2 ;
                break;
            case "5-Elvitigala-NN-11-12-10.nnet": 
                    result = (four_10.containsKey(timeZone)) ? four_10.get(timeZone) : 2 ;
                break;
            case "1-Danister-De-Silva-NN-Trai-with-11-12-10.nnet": 
                    result = (one_10.containsKey(timeZone)) ? one_10.get(timeZone) : 2 ;
                break;
            case "4-Gnanadasa-Predeepa-EW-NN-Train-with-11-12-20.nnet": 
                    result = (three_20.containsKey(timeZone)) ? three_20.get(timeZone) : 2 ;
                break;
            case "2-Gnanadasa-Predeepa-NW-NN-Trai-with-11-12-20.nnet": 
                    result = (two_20.containsKey(timeZone)) ? two_20.get(timeZone) : 2 ;
                break;
            case "5-Elvitigala-NN-11-12-20.nnet": 
                    result = (four_20.containsKey(timeZone)) ? four_20.get(timeZone) : 2 ;
                break;
            case "1-Danister-De-Silva-NN-Trai-with-11-12-20.nnet": 
                    result = (one_20.containsKey(timeZone)) ? one_20.get(timeZone) : 2 ;
                break;
        }    
        return result;
    }


}
