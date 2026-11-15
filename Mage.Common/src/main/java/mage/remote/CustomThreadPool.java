package mage.remote;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;
import org.jboss.util.threadpool.BasicThreadPool;

/**
 * Network: improved Oneway thread pool for jboss remoting instead of buggy jboss's version
 *
 * Improved features:
 * - added java 9+ compatibility (pool size errors)
 * - added idle stop and fixed memory leaks on disconnects (default pool never stops their idle threads)
 * - integrated into both client and server sides (outside of that class, see below)
 *
 * Used by:
 * - client side: ServerInvoker of any connector (e.g. a client's callback connector),
 *   created by a class name from the "onewayThreadPool" param, see Connection;
 * - server side: callback client of a server session (async mode): set by Session,
 *   jboss always creates a plain BasicThreadPool for it otherwise.
 *
 * @author JayDi85
 */
public class CustomThreadPool extends BasicThreadPool {
    private static final Logger logger = Logger.getLogger(CustomThreadPool.class);

    public CustomThreadPool() {
        enableIdleThreadExit();
    }

    public CustomThreadPool(String name) {
        super(name);
        enableIdleThreadExit();
    }

    public CustomThreadPool(String name, ThreadGroup group) {
        super(name, group);
        enableIdleThreadExit();
    }

    @Override
    public void setMaximumPoolSize(int size) {
        ThreadPoolExecutor executor = getExecutor();
        if (executor == null) {
            return;
        }
        synchronized (executor) {
            // java 9+ requires core <= max at any moment, so an order depends on a direction
            if (size >= executor.getCorePoolSize()) {
                executor.setMaximumPoolSize(size);
                executor.setCorePoolSize(size);
            } else {
                executor.setCorePoolSize(size);
                executor.setMaximumPoolSize(size);
            }
        }
    }

    private void enableIdleThreadExit() {
        ThreadPoolExecutor executor = getExecutor();
        if (executor == null) {
            return;
        }
        synchronized (executor) {
            // by default it will be stopped after 60 secs of idle
            executor.allowCoreThreadTimeOut(true);
        }
    }

    private ThreadPoolExecutor getExecutor() {
        try {
            Field executorField = BasicThreadPool.class.getDeclaredField("executor");
            executorField.setAccessible(true);
            return (ThreadPoolExecutor) executorField.get(this);
        } catch (NoSuchFieldException | SecurityException e) {
            logger.error("Failed to get field executor from BasicThreadPool", e);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            logger.error("Failed to get executor object from BasicThreadPool", e);
        }
        return null;
    }
}