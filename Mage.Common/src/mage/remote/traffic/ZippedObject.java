package mage.remote.traffic;

/**
 * Base interface for class wrapping non compressed objects.
 * Provides methods for compressing that should be used before sending it over internet and decompressing to get actual
 * data.
 *
 * @author ayrat
 */
public interface ZippedObject<T> {

    void zip(T object);

    T unzip();
}
