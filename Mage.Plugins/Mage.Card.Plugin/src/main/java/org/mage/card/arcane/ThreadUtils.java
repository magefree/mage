package org.mage.card.arcane;

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

	static public ThreadPoolExecutor threadPool;
	static private int threadCount;
	static {
		threadPool = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactory() {
			public Thread newThread (Runnable runnable) {
				threadCount++;
				Thread thread = new Thread(runnable, "Util" + threadCount);
				thread.setDaemon(true);
				return thread;
			}
		});
		threadPool.prestartAllCoreThreads();
	}

	static public void sleep (int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ignored) {
		}
	}

	static public void wait (Object lock) {
		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException ex) {
			}
		}
	}
}
