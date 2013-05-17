package signalbrook.core;

/**
 * Only works on binary inputs (can be reduced to true/false).
 */
public class Majority implements Stream<Boolean> {
    
    private volatile long wraps = 0;
    private volatile long n = 0;
    private volatile boolean current = true;
    private volatile long counter = 0;
    
    public synchronized void observe(Boolean item) throws Exception {
        if (n == Long.MAX_VALUE) {
            n = 0;
            wraps += 1;
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

    public synchronized long getFrequency(Boolean item) {
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
    
    public synchronized boolean isMajority(boolean b) {
        return current == b && counter > 0;
    }

    public boolean hasOverflowed() {
        return wraps > 0;
    }
}
