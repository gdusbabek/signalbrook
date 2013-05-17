package signalbrook.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

public class SpaceSaving<T> implements Stream<T> {
    private final double k;
    private long n = 0;
    
    private Map<T, Tuple> stored = new HashMap<T, Tuple>();
    private SortedSet<Tuple> sortedList = new TreeSet<Tuple>();
    
    public SpaceSaving(double error) {
        this.k = 1 / error;    
    }
    
    public void observe(T item) throws Exception {
        if (n == Long.MAX_VALUE) {
            throw new OverflowException("Overflowed " + Long.MAX_VALUE);
        } else {
            n += 1;
        }    
        
        Tuple t = stored.get(item);
        if (t != null) {
            t.counter.incrementAndGet();
            sortedList.remove(t);
            sortedList.add(t);
        } else if (stored.size() < (int)k) {
            t = new Tuple();
            t.item = item;
            t.counter = new AtomicLong(1);
            stored.put(item, t);
            sortedList.add(t);
        } else {
            t = sortedList.first();
            sortedList.remove(t);
            stored.remove(t.item);
            t.item = item;
            t.counter.incrementAndGet();
            stored.put(item, t);
            sortedList.add(t);
        }
        
    }

    public List<Count> getFrequentItems() {
        // we want priorityList reversed.
        Tuple[] arr = sortedList.toArray(new SpaceSaving.Tuple[sortedList.size()]);
        List<Count> list = new ArrayList<Count>(arr.length);
        Tuple item;
        for (int i = 0; i < arr.length; i++) {
            item = arr[arr.length -1 -i];
            list.add(new Count(item.item, item.counter.get()));
        }
        return list;
    }
    
    private class Tuple implements Comparable<Tuple> {
        private T item;
        private AtomicLong counter;

        public int compareTo(Tuple o) {
            return (int)(counter.get() - o.counter.get());
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof SpaceSaving.Tuple)) {
                return false;
            }
            SpaceSaving.Tuple other = (SpaceSaving.Tuple)obj;
            return item.equals(other.item);
        }
    } 
}
