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
public class RoadPech_NeuralNetwork {
    
    private NeuralNetwork neuralNetwork;

    public RoadPech_NeuralNetwork() {
        neuralNetwork = new NeuralNetwork();
    }
    
    /**
     * Traffic Forecasting switch
     * @param runtime
     * @param towerData
     * @param horizon
     * @return 
     */
    public int ForecastTrafficLevel(NeurophRuntime runtime, String towerData, int horizon){
        int trafficLevel;
        String[] tempData = towerData.split("#");
        String timeZone = tempData[2]+"#"+tempData[3];
        double userCount = Double.parseDouble(tempData[4]);
        String neuralNetworkFileName = "";
        
        if(0 == horizon){        
            switch(tempData[0]+"#"+tempData[1]){
                case "4-Gnanadasa-Predeepa-EW#79.881132-6.913941-210": 
                    neuralNetworkFileName = "4-Gnanadasa-Predeepa-EW-NN-Train-with-11-12-0.nnet";
                    break;
                case "2-Gnanadasa-Predeepa-NW#79.8765-6.91605-250": 
                    neuralNetworkFileName = "2-Gnanadasa-Predeepa-NW-NN-Trai-with-11-12-0.nnet";
                    break;
                case "5-Elvitigala#79.877082-6.909195-80": 
                    neuralNetworkFileName = "5-Elvitigala-NN-11-12-0.nnet";
                    break;
                case "1-Danister-De-Silva#79.8775-6.91679-85": 
                    neuralNetworkFileName = "1-Danister-De-Silva-NN-Trai-with-11-12-0.nnet";
                    break;
            }    
        }else if(10 == horizon){        
            switch(tempData[0]+"#"+tempData[1]){
                case "4-Gnanadasa-Predeepa-EW#79.881132-6.913941-210": 
                    neuralNetworkFileName = "4-Gnanadasa-Predeepa-EW-NN-Train-with-11-12-10.nnet";
                    break;
                case "2-Gnanadasa-Predeepa-NW#79.8765-6.91605-250": 
                    neuralNetworkFileName = "2-Gnanadasa-Predeepa-NW-NN-Trai-with-11-12-10.nnet";
                    break;
                case "5-Elvitigala#79.877082-6.909195-80": 
                    neuralNetworkFileName = "5-Elvitigala-NN-11-12-10.nnet";
                    break;
                case "1-Danister-De-Silva#79.8775-6.91679-85": 
                    neuralNetworkFileName = "1-Danister-De-Silva-NN-Trai-with-11-12-10.nnet";
                    break;
            }    
        }else if(20 == horizon){        
            switch(tempData[0]+"#"+tempData[1]){
                case "4-Gnanadasa-Predeepa-EW#79.881132-6.913941-210": 
                    neuralNetworkFileName = "4-Gnanadasa-Predeepa-EW-NN-Train-with-11-12-20.nnet";
                    break;
                case "2-Gnanadasa-Predeepa-NW#79.8765-6.91605-250": 
                    neuralNetworkFileName = "2-Gnanadasa-Predeepa-NW-NN-Trai-with-11-12-20.nnet";
                    break;
                case "5-Elvitigala#79.877082-6.909195-80": 
                    neuralNetworkFileName = "5-Elvitigala-NN-11-12-20.nnet";
                    break;
                case "1-Danister-De-Silva#79.8775-6.91679-85": 
                    neuralNetworkFileName = "1-Danister-De-Silva-NN-Trai-with-11-12-20.nnet";
                    break;
            }    
        }
        
        trafficLevel = this.neuralNetwork.Traffic_Forecast(runtime, neuralNetworkFileName, timeZone, userCount);
        
        return trafficLevel;
    }

}
