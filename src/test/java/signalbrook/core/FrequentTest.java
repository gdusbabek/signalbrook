package signalbrook.core;

import com.google.common.collect.Lists;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FrequentTest {
    
    @Test
    public void simpleTest() throws Exception {
        Frequent<Long> freq = new Frequent<Long>(0.001d);
        
        for (int i = 0; i < 1000000; i++) {
            freq.observe((long)Random.discrete(Random.DISCRETE_BIMODAL_500));
        }
        
        // ok. lets
        List<Count> frequentItems = freq.getFrequentItems();
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
}
