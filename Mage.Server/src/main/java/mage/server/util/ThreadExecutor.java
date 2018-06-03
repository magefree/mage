
package mage.server.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public enum ThreadExecutor {
instance;
    private static final ExecutorService callExecutor = Executors.newCachedThreadPool();
    private static final ExecutorService userExecutor = Executors.newCachedThreadPool();
    private static final ExecutorService gameExecutor = Executors.newFixedThreadPool(ConfigSettings.instance.getMaxGameThreads());
    private static final ScheduledExecutorService timeoutExecutor = Executors.newScheduledThreadPool(4);
    private static final ScheduledExecutorService timeoutIdleExecutor = Executors.newScheduledThreadPool(4);

    /**
     * noxx: what the settings below do is setting the ability to keep OS
     * threads for new games for 60 seconds If there is no new game created
     * within this time period, the thread may be discarded. But anyway if new
     * game is created later, new OS/java thread will be created for it taking
     * MaxGameThreads limit into account.
     *
     * This all is done for performance reasons as creating new OS threads is
     * resource consuming process.
     */
    static {
        ((ThreadPoolExecutor) callExecutor).setKeepAliveTime(60, TimeUnit.SECONDS);
        ((ThreadPoolExecutor) callExecutor).allowCoreThreadTimeOut(true);
        ((ThreadPoolExecutor) callExecutor).setThreadFactory(new XMageThreadFactory("CALL"));
        ((ThreadPoolExecutor) userExecutor).setKeepAliveTime(60, TimeUnit.SECONDS);
        ((ThreadPoolExecutor) userExecutor).allowCoreThreadTimeOut(true);
        ((ThreadPoolExecutor) userExecutor).setThreadFactory(new XMageThreadFactory("USER"));
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


    public int getActiveThreads(ExecutorService executerService) {
        if (executerService instanceof ThreadPoolExecutor) {
            return ((ThreadPoolExecutor) executerService).getActiveCount();
        }
        return -1;
    }

    public ExecutorService getCallExecutor() {
        return callExecutor;
    }

    public ExecutorService getGameExecutor() {
        return gameExecutor;
    }

    public ScheduledExecutorService getTimeoutExecutor() {
        return timeoutExecutor;
    }

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
