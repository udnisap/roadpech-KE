/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roadpech_initialization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author Rashmika
 */
public class FileBreaker {
    
    public static void main(String[] args) {
        
        FileBreaker test = new FileBreaker();
        
        ArrayList<InMobitelSubscriberData> in_mobitel_subscriber_data = test.readMobitelSubscriberFromFile("mobitel_in_data//20130614.txt");

        ArrayList<In_Mobitel_Combined_Date> combined_data = new ArrayList<In_Mobitel_Combined_Date>();
        
        int i=0, j=0;
        
        for(InMobitelSubscriberData subscriber_data : in_mobitel_subscriber_data){
            
            
            
        }
    }
    
    public ArrayList<InMobitelSubscriberData> readMobitelSubscriberFromFile(String filename){
        BufferedReader br = null;
        ArrayList<InMobitelSubscriberData> indata = new ArrayList<InMobitelSubscriberData>();
 
        try {

                String sCurrentLine;
                String[] sCurrentContent;
                
                String imsi;
                Date date;
                String[] temp_date;
                Time time;
                String[] temp_time;
                String cgi;

                br = new BufferedReader(new FileReader(filename));
                
                int i=0, j=0;
                String content = "";
                
                while ((sCurrentLine = br.readLine()) != null) {
                    content += sCurrentLine;
                    if(i==10000){
                        i=0;
                        writeFile(content, "mobitel_in_data//20130614_modified_"+j+".txt");
                        j++;
                    }
                    
                }

        } catch (IOException e) {
                e.printStackTrace();
        } finally {
                try {
                        if (br != null)br.close();
                } catch (IOException ex) {
                        ex.printStackTrace();
                }
                return indata;
        }
    }
    
    public void writeFile(String content, String filename){
        try {
  
            File file = new File(filename);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                    file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
