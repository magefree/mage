package mage.utils;

import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * Util method to work with threads.
 *
 * @author ayrat
 */
public final class ThreadUtils {

    private static final Logger logger = Logger.getLogger(ThreadUtils.class);

    public static final ThreadPoolExecutor threadPoolSounds;
    public static final ThreadPoolExecutor threadPoolPopups;
    private static int threadCount;

    static {
        threadPoolSounds = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                threadCount++;
                Thread thread = new Thread(runnable, "SOUND-" + threadCount);
                thread.setDaemon(true);
                return thread;
            }
        }) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                t = findRealException(r, t);
                if (t != null) {
                    // TODO: show sound errors in client logs?
                    //logger.error("Catch unhandled error in SOUND thread: " + t.getMessage(), t);
                }
            }
        };
        threadPoolSounds.prestartAllCoreThreads();

        threadPoolPopups = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                threadCount++;
                Thread thread = new Thread(runnable, "POPUP-" + threadCount);
                thread.setDaemon(true);
                return thread;
            }
        }) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);

                // catch errors in popup threads (example: card popup over cards or chat/log messages)
                t = findRealException(r, t);
                if (t != null) {
                    logger.error("Catch unhandled error in POPUP thread: " + t.getMessage(), t);
                }
            }
        };
        threadPoolPopups.prestartAllCoreThreads();
    }

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
     * @param r
     * @param t
     * @return
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
