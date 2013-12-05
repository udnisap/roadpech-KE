/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import BasicEntity.SampleDate;
import KnowledgeEngine.EngineMain;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Rashmika
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ProcessTest.class})
public class OutputTest extends TestCase{

    public void testOutput() {
        boolean result = false;
        
        SampleDate dateVal = SampleDate.D14;
        EngineMain main = new EngineMain();
        result = main.Play(dateVal);
        
        Assert.assertTrue(result);
    }    
    
}