package mage.remote;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;
import org.jboss.util.threadpool.BasicThreadPool;

public class CustomThreadPool extends BasicThreadPool {
    private static final Logger logger = Logger.getLogger(CustomThreadPool.class);

    /**
     * JBoss remoting creates this pool for every connection and never stops it: neither
     * Client.disconnect() nor ServerInvoker.stop()/destroy() touch the pool, so every closed
     * connection leaks its worker threads for the rest of the process lifetime (the executor
     * starts core=max=4 and core threads never die). Idle threads are allowed to die instead
     * and are re-created on the next task, which bounds the leak to active connections.
     * Oneway send ordering is unaffected: the callback pool is a plain BasicThreadPool
     * configured with maxNumThreadsOneway=1 (see Connection), not this class.
     */
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
        /*
         * I really don't want to implement a whole new threadpool
         * just to fix this and the executor is private
         */
        try {
            Field executorField = BasicThreadPool.class.getDeclaredField("executor");
            executorField.setAccessible(true);
            ThreadPoolExecutor executor = (ThreadPoolExecutor) executorField.get(this);
            synchronized (executor) {
                executor.setMaximumPoolSize(size);
                executor.setCorePoolSize(size);
            }
        } catch (NoSuchFieldException | SecurityException e) {
            logger.error("Failed to get field executor from BasicThreadPool", e);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            logger.error("Failed to get executor object from BasicThreadPool", e);
        }
    }

    private void enableIdleThreadExit() {
        try {
            Field executorField = BasicThreadPool.class.getDeclaredField("executor");
            executorField.setAccessible(true);
            ThreadPoolExecutor executor = (ThreadPoolExecutor) executorField.get(this);
            synchronized (executor) {
                executor.allowCoreThreadTimeOut(true);
            }
        } catch (NoSuchFieldException | SecurityException e) {
            logger.error("Failed to get field executor from BasicThreadPool", e);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            logger.error("Failed to get executor object from BasicThreadPool", e);
        }
    }
}