package signalbrook.core;

public interface Stream<T> {
    public void observe(T item) throws Exception;
    public long getFrequency(T item);
    public boolean hasOverflowed();
}
