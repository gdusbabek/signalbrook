package signalbrook.core;

public final class Count<T> implements Comparable<Count> {
    private final T item;
    private final long count;
    
    public Count(T item, long count) {
        this.item = item;
        this.count = count;
    }
    
    public T getItem() {
        return item;
    }

    public long getCount() {
        return count;
    }

    public int compareTo(Count o) {
        return (int)(count - o.getCount());
    }
    
    public String toString() {
        return item.toString() + ":" + count;
    }
}
