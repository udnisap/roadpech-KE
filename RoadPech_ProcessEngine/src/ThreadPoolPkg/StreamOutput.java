/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadPoolPkg;

import ProcessEngine.ProcessEngineMain;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rashmika
 */
public class StreamOutput implements Runnable{
    
    private ProcessEngineMain main;

    public StreamOutput(ProcessEngineMain main) {
        this.main = main;
    }

    @Override
    public void run() {
        
        while(true){
            main.update();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(StreamOutput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
   
}
