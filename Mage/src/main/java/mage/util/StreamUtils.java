package mage.util;

import java.io.Closeable;

public final class StreamUtils {

    /***
     * Quietly closes the closable, ignoring nulls and exceptions
     * @param c - the closable to be closed
     */
    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            }
            catch (Exception e) {
            }
        }
    }

    public static void closeQuietly(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            }
            catch (Exception e) {
            }
        }
    }
}
