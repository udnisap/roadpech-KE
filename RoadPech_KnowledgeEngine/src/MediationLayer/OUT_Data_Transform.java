/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MediationLayer;

import BasicEntity.SampleDate;
import DBFacade.Facade_Out_KB;
import java.util.ArrayList;

/**
 *
 * @author Rashmika
 */
public class OUT_Data_Transform {
    
    private Facade_Out_KB out_facade;
    
    public OUT_Data_Transform(){
        out_facade = new Facade_Out_KB();
    }
    
    /**
     * Write output to a csv file
     * @param dataList
     * @param dateVal
     * @return 
     */
    public boolean writeCSV(ArrayList<String> dataList, SampleDate dateVal){
        return out_facade.WirteCSV(dataList, dateVal);
    }
    
}
