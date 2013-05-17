package signalbrook.core;

import java.util.List;

public interface Stream<T> {
    public void observe(T item) throws Exception;
    public List<Count> getFrequentItems();
}
