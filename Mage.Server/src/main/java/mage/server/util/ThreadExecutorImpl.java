package mage.server.util;

import mage.server.managers.ConfigSettings;
import mage.server.managers.ThreadExecutor;
import mage.utils.ThreadUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class ThreadExecutorImpl implements ThreadExecutor {

    private static final Logger logger = Logger.getLogger(ThreadExecutorImpl.class);

    private final ExecutorService callExecutor; // shareable threads to run single task (example: save new game settings from a user, send chat message, etc)
    private final ExecutorService gameExecutor; // game threads to run long tasks, one per game (example: run game and wait user's feedback)
    private final ScheduledExecutorService timeoutExecutor;
    private final ScheduledExecutorService timeoutIdleExecutor;

    /**
     * noxx: what the settings below do is setting the ability to keep OS
     * threads for new games for 60 seconds If there is no new game created
     * within this time period, the thread may be discarded. But anyway if new
     * game is created later, new OS/java thread will be created for it taking
     * MaxGameThreads limit into account.
     * <p>
     * This all is done for performance reasons as creating new OS threads is
     * resource consuming process.
     */

    public ThreadExecutorImpl(ConfigSettings config) {
        //callExecutor = Executors.newCachedThreadPool();
        callExecutor = new CachedThreadPoolWithException();
        //gameExecutor = Executors.newFixedThreadPool(config.getMaxGameThreads());
        gameExecutor = new FixedThreadPoolWithException(config.getMaxGameThreads());
        timeoutExecutor = Executors.newScheduledThreadPool(4);
        timeoutIdleExecutor = Executors.newScheduledThreadPool(4);

        ((ThreadPoolExecutor) callExecutor).setKeepAliveTime(60, TimeUnit.SECONDS);
        ((ThreadPoolExecutor) callExecutor).allowCoreThreadTimeOut(true);
        ((ThreadPoolExecutor) callExecutor).setThreadFactory(new XMageThreadFactory("CALL"));
        ((ThreadPoolExecutor) gameExecutor).setKeepAliveTime(60, TimeUnit.SECONDS);
        ((ThreadPoolExecutor) gameExecutor).allowCoreThreadTimeOut(true);
        ((ThreadPoolExecutor) gameExecutor).setThreadFactory(new XMageThreadFactory("GAME"));
        ((ThreadPoolExecutor) timeoutExecutor).setKeepAliveTime(60, TimeUnit.SECONDS);
        ((ThreadPoolExecutor) timeoutExecutor).allowCoreThreadTimeOut(true);
        ((ThreadPoolExecutor) timeoutExecutor).setThreadFactory(new XMageThreadFactory("TIMEOUT"));
        ((ThreadPoolExecutor) timeoutIdleExecutor).setKeepAliveTime(60, TimeUnit.SECONDS);
        ((ThreadPoolExecutor) timeoutIdleExecutor).allowCoreThreadTimeOut(true);
        ((ThreadPoolExecutor) timeoutIdleExecutor).setThreadFactory(new XMageThreadFactory("TIMEOUT_IDLE"));
    }

    static class CachedThreadPoolWithException extends ThreadPoolExecutor {

        CachedThreadPoolWithException() {
            // use same params as Executors.newCachedThreadPool()
            super(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);

            // catch errors in CALL threads (from client commands)
            t = ThreadUtils.findRealException(r, t);
            if (t != null) {
                logger.error("Catch unhandled error in CALL thread: " + t.getMessage(), t);
            }
        }
    }

    static class FixedThreadPoolWithException extends ThreadPoolExecutor {

        FixedThreadPoolWithException(int nThreads) {
            // use same params as Executors.newFixedThreadPool()
            super(nThreads, nThreads,0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);

            // catch errors in GAME threads (from game processing)
            t = ThreadUtils.findRealException(r, t);
            if (t != null) {
                // it's impossible to brake game thread in normal use case, so each bad use case must be researched
                logger.error("Catch unhandled error in GAME thread: " + t.getMessage(), t);
            }
        }
    }

    @Override
    public int getActiveThreads(ExecutorService executerService) {
        if (executerService instanceof ThreadPoolExecutor) {
            return ((ThreadPoolExecutor) executerService).getActiveCount();
        }
        return -1;
    }

    @Override
    public ExecutorService getCallExecutor() {
        return callExecutor;
    }

    @Override
    public ExecutorService getGameExecutor() {
        return gameExecutor;
    }

    @Override
    public ScheduledExecutorService getTimeoutExecutor() {
        return timeoutExecutor;
    }

    @Override
    public ScheduledExecutorService getTimeoutIdleExecutor() {
        return timeoutIdleExecutor;
    }

}

class XMageThreadFactory implements ThreadFactory {

    private final String prefix;

    XMageThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(prefix + ' ' + thread.getThreadGroup().getName() + '-' + thread.getId());
        return thread;
    }
}
