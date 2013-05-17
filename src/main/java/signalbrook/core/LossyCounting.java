package signalbrook.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LossyCounting<T> implements Stream<T> {
    private boolean over = false;
    private long housekeepingCount = 0;
    
    private long n = 0;
    private double delta = 0;
    private final Map<T, Tuple> stored = new HashMap<T, Tuple>();
    
    private final double k;
    
    public LossyCounting(double error) {
        this.k = 1d / error;
    }
    
    public void observe(T item) throws Exception {
        if (n == Long.MAX_VALUE) {
            throw new OverflowException("Overflowed " + Long.MAX_VALUE);
        } else {
            n += 1;
        }
        
        Tuple t = stored.get(item);
        if (t != null) {
            t.lowerBound += 1;
        } else {
            t = new Tuple();
            t.item = item;
            t.lowerBound = 1;
            t.delta = Math.floor((double)(n-1) / k);
            stored.put(item, t);
        }
        
        // housekeeping. different heuristics can work here.
//        if ((double)n / k != this.delta) {
        if (n % 1000 == 0) {
            housekeepingCount += 1;
            delta = (double)n / k;
            List<T> toRemove = new ArrayList<T>();
            for (Map.Entry<T, Tuple> entry : stored.entrySet()) {
                if (entry.getValue().lowerBound < delta) {
                    toRemove.add(entry.getKey());
                }
            }
            for (T key : toRemove) {
                stored.remove(key);
            }
        }
    }
    
    public List<Count> getFrequentItems() {
        List<Count> list = new ArrayList<Count>();
        for (Map.Entry<T, Tuple> entry : stored.entrySet()) {
            list.add(new Count(entry.getKey(), entry.getValue().lowerBound));
        }
        Collections.sort(list);
        return list;
    }
    
    private class Tuple {
        private T item;
        private long lowerBound;
        private double delta;
    }
}
