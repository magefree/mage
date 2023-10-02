package mage.utils;

import mage.remote.traffic.ZippedObject;
import mage.remote.traffic.ZippedObjectImpl;

/**
 * Helps to compress and decompress network data
 *
 * @author ayrat
 */
public final class CompressUtil {

    // disable data compression in client-server traffic, add java param like -Dxmage.network.nocompress
    private static final String NO_COMPRESS_DATA_PROPERTY = "xmage.network.nocompress";
    private static final boolean compressData;

    static {
        compressData = System.getProperty(NO_COMPRESS_DATA_PROPERTY) == null;
    }

    public static Object decompress(Object data) {
        if (!(data instanceof ZippedObject)) {
            return data;
        }
        return ((ZippedObject) data).unzip();
    }

    public static Object compress(Object data) {
        if (data != null && compressData) {
            return new ZippedObjectImpl<>(data);
        }
        return data;
    }
}
