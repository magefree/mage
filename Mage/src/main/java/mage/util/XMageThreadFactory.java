package mage.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Helper class to give debug friendly names for a threads
 *
 * @author JayDi85
 */
public class XMageThreadFactory implements ThreadFactory {

    private final String prefix;
    private final AtomicInteger counter = new AtomicInteger();
    private final boolean isDaemon;

    public XMageThreadFactory(String prefix) {
        this(prefix, true);
    }

    /**
     * @param prefix   thread's starting name (can be changed by thread itself later)
     * @param isDaemon mark thread as daemon on non-writeable tasks (e.g. can be terminated at any time without data loss)
     */
    public XMageThreadFactory(String prefix, boolean isDaemon) {
        this.prefix = prefix;
        this.isDaemon = isDaemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        int instanceNumber = this.counter.incrementAndGet();

        Thread thread = new Thread(r);
        thread.setDaemon(this.isDaemon);

        // gives default name, but threads can change it by Thread.currentThread().setName (example: on game or tourney start)
        thread.setName(String.format("%s - %d", this.prefix, instanceNumber));

        return thread;
    }
}
