package signalbrook.core;

public interface Stream<T> {
    public void observe(T item) throws Exception;
    public boolean hasOverflowed();
}
