package mage.utils;

import mage.remote.traffic.ZippedObject;
import mage.remote.traffic.ZippedObjectImpl;

/**
 * Helps to compress and decompress data if needed.
 *
 * @author ayrat
 */
public class CompressUtil {

    /**
     * Defines should data be compressed or not. True by default.
     * Read from system property:
     */
    private static boolean compressData = true;

    /**
     * Defines the system property name to disable any compressing.
     */
    private static final String NO_COMPRESS_DATA_PROPERTY = "nocompress";

    static {
        compressData = System.getProperty(NO_COMPRESS_DATA_PROPERTY) == null;
    }

    /**
     * Hidden constructor
     */
    private CompressUtil() {}

    /**
     * Decompress data, but only if it was compressed previously return original object otherwise.
     *
     * @param data Data to decompress
     * @return Decompressed object
     */
    public static Object decompress(Object data) {
        if (data == null || !(data instanceof ZippedObject)) {
            return data;
        }
        return ((ZippedObject)data).unzip();
    }

    /**
     * Compress data.
     *
     * @param data Data to compress
     * @return Compressed object
     */
    public static Object compress(Object data) {
        if (data != null && compressData) {
            return new ZippedObjectImpl<Object>(data);
        }
        return null;
    }
}
