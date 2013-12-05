/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roadpech_neuralnetwork;

import neuroph.runtime.NeurophRuntime;

/**
 *
 * @author Rashmika
 */
public class NeuralNetwork {
    
    /**
     * Forecast Traffic values using NN model
     * @param runtime
     * @param neuralNetworkFile
     * @param timeZone
     * @param userCount
     * @return int predicted traffic level
     */
    public int Traffic_Forecast(NeurophRuntime runtime, String neuralNetworkFile, String timeZone, double userCount){
        
        int finalTrafficLevel; 
        
        //Loading the NN instance
        org.neuroph.core.NeuralNetwork nnet = org.neuroph.core.NeuralNetwork.load(neuralNetworkFile);
        
        //Add the NN to Neuroph Runtime
        runtime.load(neuralNetworkFile, nnet);
        
        //Process time interval into bits sequence
        String processsedTimeZone = PreProcessor.toCharSequence(timeZone);
        
        //Set the input parameters into the NN
        nnet.setInput(processsedTimeZone.charAt(0),processsedTimeZone.charAt(1),processsedTimeZone.charAt(2)
                ,processsedTimeZone.charAt(3),processsedTimeZone.charAt(4)
                ,processsedTimeZone.charAt(5), userCount);
        
        //Calculate the NN
        nnet.calculate();

        //Retrieve the predicted traffic value
        finalTrafficLevel = runtime.output(neuralNetworkFile, timeZone);      
                
        return finalTrafficLevel;
    }
    
}
