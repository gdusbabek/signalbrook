package signalbrook.core;

import junit.framework.Assert;
import org.junit.Test;

public class MajorityTest {
    
    @Test
    public void testEmpty() {
        // there should be no majority.
        Majority m = new Majority();
        Assert.assertFalse(m.isMajority(false));
        Assert.assertFalse(m.isMajority(true));
        Assert.assertFalse(m.isMajority(Boolean.FALSE));
        Assert.assertFalse(m.isMajority(Boolean.TRUE));
    }
    
    @Test
    public void testBalancing() throws Exception {
        Majority m = new Majority();
        for (int i = 0; i < 10; i++) {
            m.observe(true);
        }
        Assert.assertTrue(m.isMajority(true));
        Assert.assertEquals(10, m.getFrequency(true));
        Assert.assertEquals(0, m.getFrequency(false));
        
        for (int i = 0; i < 10; i++) {
            m.observe(false);
        }
        Assert.assertFalse(m.isMajority(true));
        Assert.assertFalse(m.isMajority(false));
        Assert.assertEquals(10, m.getFrequency(true));
        Assert.assertEquals(10, m.getFrequency(false));
        
        // tip it towards false.
        m.observe(false);
        
        Assert.assertTrue(m.isMajority(false));
        Assert.assertFalse(m.isMajority(true));
        Assert.assertEquals(11, m.getFrequency(false));
        Assert.assertEquals(10, m.getFrequency(true));
    }
}
