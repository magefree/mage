package mage.utils;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Util method to work with threads.
 *
 * @author ayrat
 */
public final class ThreadUtils {

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public static void wait(Object lock) {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ex) {
            }
        }
    }

    /**
     * Find real exception object after thread task completed. Can be used in afterExecute
     *
     */
    public static Throwable findRealException(Runnable r, Throwable t) {
        // executer.submit - return exception in result
        // executer.execute - return exception in t
        if (t == null && r instanceof Future<?>) {
            try {
                Object result = ((Future<?>) r).get();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
        return t;
    }
}
