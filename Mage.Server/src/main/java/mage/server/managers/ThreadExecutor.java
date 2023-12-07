package mage.server.managers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public interface ThreadExecutor {
    int getActiveThreads(ExecutorService executerService);

    ExecutorService getCallExecutor();

    ExecutorService getGameExecutor();

    ScheduledExecutorService getTimeoutExecutor();

    ScheduledExecutorService getTimeoutIdleExecutor();

    ScheduledExecutorService getServerHealthExecutor();
}
