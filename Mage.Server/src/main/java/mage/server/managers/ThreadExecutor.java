package mage.server.managers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Server side threads to execute some code/tasks
 */
public interface ThreadExecutor {

    int getActiveThreads(ExecutorService executerService);

    /**
     * Main game thread (one per game)
     */
    ExecutorService getGameExecutor();

    /**
     * Tourney thread
     */
    ExecutorService getTourneyExecutor();

    /**
     * Helper threads to execute async commands for game and server related tasks (example: process income command from a client)
     */
    ExecutorService getCallExecutor();

    /**
     * Helper threads to execute async timers and time related tasks
     */
    ScheduledExecutorService getTimeoutExecutor();

    ScheduledExecutorService getTimeoutIdleExecutor();

    /**
     * Helper thread to execute inner server tasks
     */
    ScheduledExecutorService getServerHealthExecutor();
}
