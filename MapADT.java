import java.util.NoSuchElementException;

public interface MapADT<KeyType, ValueType> {

    boolean put(KeyType key, ValueType value);

    ValueType get(KeyType key) throws NoSuchElementException;

    int size();

    boolean containsKey(KeyType key);

    ValueType remove(KeyType key);

    void clear();

}
