package signalbrook.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Only works on binary inputs (can be reduced to true/false).
 */
public class Majority implements Stream<Boolean> {
    
    private long n = 0;
    private boolean current = true;
    private long counter = 0;
    
    public void observe(Boolean item) throws Exception {
        if (n == Long.MAX_VALUE) {
            throw new OverflowException("Overflowed " + Long.MAX_VALUE);
        } else {
            n += 1;
        }
        
        if (current == item) {
            counter += 1;
        } else if (counter == 0) {
            current = item;
            counter = 1;
        } else {
            counter -= 1;
        }
    }

    public long getFrequency(Boolean item) {
        // invariant: major + minor == n.
        // this is busted after overflow, btw.
        long major = n / 2;
        long minor = n / 2 + (n % 2);
        while (major - minor != counter) {
            major += 1;
            minor -= 1;
        }
        return current == item ? major : minor;
    }
    
    public boolean isMajority(boolean b) {
        return current == b && counter > 0;
    }

    public List<Count> getFrequentItems() {
        List<Count> list = new ArrayList<Count>(2);
        list.add(new Count<Boolean>(Boolean.TRUE, getFrequency(Boolean.TRUE)));
        list.add(new Count<Boolean>(Boolean.FALSE, getFrequency(Boolean.FALSE)));
        Collections.sort(list);
        return list;
    }
}
