/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.TestCase;
import neuroph.runtime.NeurophRuntime;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import roadpech_neuralnetwork.NeuralNetwork;

/**
 *
 * @author Rashmika
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({})
public class NeuralNetworkTest extends TestCase {

    public void testNeuralNetworkResult() {
        NeuralNetwork nn = new NeuralNetwork();
        NeurophRuntime runtime = new NeurophRuntime();
        int i = nn.Traffic_Forecast(runtime,"1-Danister-De-Silva-NN-Trai-with-11-12-0.nnet", "10:20:00#10:30:00", 0.060240963855421686);
        System.out.println(""+i);
        Assert.assertEquals(i, 3);
    }
    
}