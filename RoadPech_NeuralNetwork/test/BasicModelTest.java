/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import BasicModel.BasicModelwithTime;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Rashmika
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({NeuralNetworkTest.class})
public class BasicModelTest extends TestCase{

    public void testPrediction_Current() {
        BasicModelwithTime nn = new BasicModelwithTime();
        int i = nn.ForecastTrafficLevel("4-Gnanadasa-Predeepa-EW#79.881132-6.913941-210#06:00:00#06:10:00#0.0602409", 10);
        Assert.assertEquals(i, 1);
    }
    
    public void testPrediction_10() {
        BasicModelwithTime nn = new BasicModelwithTime();
        int i = nn.ForecastTrafficLevel("4-Gnanadasa-Predeepa-EW#79.881132-6.913941-210#11:00:00#11:10:00#0.0602409", 10);
        Assert.assertEquals(i, 2);
    }
    
    public void testPrediction_20() {
        BasicModelwithTime nn = new BasicModelwithTime();
        int i = nn.ForecastTrafficLevel("4-Gnanadasa-Predeepa-EW#79.881132-6.913941-210#14:00:00#14:10:00#0.0602409", 10);
        Assert.assertEquals(i, 3);
    }
}