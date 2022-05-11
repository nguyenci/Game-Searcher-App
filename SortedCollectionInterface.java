public interface SortedCollectionInterface<T extends Comparable<T>> extends Iterable<T> {
    // Note that the provided iterators step through the data within this
    // collection in sorted order, as defined by their compareTo() method.

    boolean insert(T data) throws NullPointerException, IllegalArgumentException;

    boolean contains(T data);

    int size();

    boolean isEmpty();

}
