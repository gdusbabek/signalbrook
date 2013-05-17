package signalbrook.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Frequent<T> implements Stream<T> {
    private long n = 0;
    private boolean over = false;
    
    private int k;
    private final Map<T, AtomicLong> stored;
    
    public Frequent(double error) {
        this.k = (int)Math.ceil(1d / error);
        stored = new HashMap<T, AtomicLong>(k - 1);
    }
    
    public void observe(T item) throws Exception {
        if (n == Long.MAX_VALUE) {
            throw new OverflowException("Overflowed " + Long.MAX_VALUE);
        } else {
            n += 1;
        }
        
        AtomicLong counter = stored.get(item);
        if (counter != null) {
            counter.incrementAndGet();
        } else if (stored.size() < k) {
            stored.put(item, new AtomicLong(1));
        } else {
            long newValue;
            List<T> toRemove = new ArrayList<T>();
            
            // decrement every body.
            for (Map.Entry<T, AtomicLong> entry : stored.entrySet()) {
                newValue = entry.getValue().decrementAndGet();
                if (newValue == 0) {
                    toRemove.add(entry.getKey());
                }
            }
            
            // remove counters at zero.
            if (toRemove.size() > 0) {
                for (T t : toRemove) {
                    stored.remove(t);
                }
            }
        }
    }
    
    public List<Count> getFrequentItems() {
        List<Count> list = new ArrayList<Count>();
        for (Map.Entry<T, AtomicLong> entry : stored.entrySet()) {
            list.add(new Count(entry.getKey(), entry.getValue().get()));
        }
        Collections.sort(list);
        return list;
    }
    
}
