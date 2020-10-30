package mage.server.util;

import mage.server.managers.ConfigSettings;
import mage.server.managers.ThreadExecutor;

import java.util.concurrent.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ThreadExecutorImpl implements ThreadExecutor {
    private final ExecutorService callExecutor;
    private final ExecutorService gameExecutor;
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
        callExecutor = Executors.newCachedThreadPool();
        gameExecutor = Executors.newFixedThreadPool(config.getMaxGameThreads());
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
