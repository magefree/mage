/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
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
public class ThreadExecutor {

    private static final ExecutorService callExecutor = Executors.newCachedThreadPool();
    private static final ExecutorService userExecutor = Executors.newCachedThreadPool();
    private static final ExecutorService gameExecutor = Executors.newFixedThreadPool(ConfigSettings.getInstance().getMaxGameThreads());
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

    private static final ThreadExecutor INSTANCE = new ThreadExecutor();

    public static ThreadExecutor getInstance() {
        return INSTANCE;
    }

    private ThreadExecutor() {
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
