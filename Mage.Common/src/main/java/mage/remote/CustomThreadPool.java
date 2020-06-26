package mage.remote;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;
import org.jboss.util.threadpool.BasicThreadPool;

public class CustomThreadPool extends BasicThreadPool {
    private static final Logger logger = Logger.getLogger(SessionImpl.class);

    @Override
    public void setMaximumPoolSize(int size) {
        /*
         * I really don't want to implement a whole new threadpool
         * just to fix this and the executor is private
         */
        try {
            Field executorField = BasicThreadPool.class.getField("executor");
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
}