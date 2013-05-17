package signalbrook.core;

import com.google.common.collect.Lists;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FrequentTest {
    
    private void testFrequencyStream(Stream<Long> stream) throws Exception {
        for (int i = 0; i < 1000000; i++) {
            stream.observe((long) Random.discrete(Random.DISCRETE_BIMODAL_500));
        }
        
        // ok. lets
        List<Count> frequentItems = stream.getFrequentItems();
        Collections.reverse(frequentItems);
        
        // these should represent the two modes of the bimodal distribution (50 and 450). counts should be roughly equal.
        List<Count> topTwo = Lists.newArrayList(frequentItems.get(0), frequentItems.get(1));
        Collections.sort(topTwo, new Comparator<Count>() {
            public int compare(Count o1, Count o2) {
                return (int)((Long)o1.getItem() - (Long)o2.getItem());
            }
        });
        
        Assert.assertEquals(50L, topTwo.get(0).getItem());
        Assert.assertEquals(450L, topTwo.get(1).getItem());
    }
    @Test
    public void frequentTest() throws Exception {
        Frequent<Long> stream = new Frequent<Long>(0.001d);
        testFrequencyStream(stream);
    }
    
    @Test
    public void lossyTest() throws Exception {
        LossyCounting<Long> stream = new LossyCounting<Long>(0.001d);
        testFrequencyStream(stream);
    }
    
    @Test
    public void spaceSavingTest() throws Exception {
        SpaceSaving<Long> stream = new SpaceSaving<Long>(0.00001d);
        testFrequencyStream(stream);
    }
}
