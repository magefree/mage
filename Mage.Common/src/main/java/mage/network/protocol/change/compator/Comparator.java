package mage.network.protocol.change.compator;

/**
 * Compares two objects
 *
 * @author magenoxx
 */
public interface Comparator<T> {

    /**
     * Compares two objects and returns {@link CompareResult} with map of partly updates
     *
     * @param objectA object to compare with
     * @param objectB object to compare with
     * @return result of compare
     */
    CompareResult compare(T objectA, T objectB);
}
