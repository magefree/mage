package mage.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Util method to work with threads.
 *
 * @author ayrat
 */
public class ThreadUtils {

    public static final ThreadPoolExecutor threadPool;
    public static final ThreadPoolExecutor threadPool2;
    private static int threadCount;
    static {
        threadPool = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactory() {
            @Override
            public Thread newThread (Runnable runnable) {
                threadCount++;
                Thread thread = new Thread(runnable, "Util" + threadCount);
                thread.setDaemon(true);
                return thread;
            }
        });
        threadPool.prestartAllCoreThreads();
        threadPool2 = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactory() {
            @Override
            public Thread newThread (Runnable runnable) {
                threadCount++;
                Thread thread = new Thread(runnable, "TP2" + threadCount);
                thread.setDaemon(true);
                return thread;
            }
        });
        threadPool2.prestartAllCoreThreads();
    }

    public static void sleep (int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public static void wait (Object lock) {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ex) {
            }
        }
    }
}
