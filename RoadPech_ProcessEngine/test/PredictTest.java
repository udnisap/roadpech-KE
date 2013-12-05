/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ProcessEngine.ProcessEngineMain;

/**
 *
 * @author Rashmika
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({})
public class PredictTest extends TestCase{

    public void testBasicModel_Current() {
        ProcessEngineMain nn = new ProcessEngineMain();
        String str = nn.predict("11th Tuesday", "Basic Model Real Time");
        Assert.assertNotNull(str);
    }
    
    public void testBasicModel_10Min() {
        ProcessEngineMain nn = new ProcessEngineMain();
        String str = nn.predict("12th Wednesday", "Basic Model 10 Min");
        Assert.assertNotNull(str);
    }
    
    public void testBasicModel_20Min() {
        ProcessEngineMain nn = new ProcessEngineMain();
        String str = nn.predict("14th Friday", "Basic Model 20 Min");
        Assert.assertNotNull(str);
    }
    
    public void testNeuralNetworkModel_Current() {
        ProcessEngineMain nn = new ProcessEngineMain();
        String str = nn.predict("11th Tuesday", "NN Model Real Time");
        Assert.assertNotNull(str);
    }
    
    public void testNeuralNetworkModel_10Min() {
        ProcessEngineMain nn = new ProcessEngineMain();
        String str = nn.predict("12th Wednesday", "NN Model Real 10 Min");
        Assert.assertNotNull(str);
    }
    
    public void testNeuralNetworkModel_20Min() {
        ProcessEngineMain nn = new ProcessEngineMain();
        String str = nn.predict("14th Friday", "NN Model Real 20 Min");
        Assert.assertNotNull(str);
    }
    
}