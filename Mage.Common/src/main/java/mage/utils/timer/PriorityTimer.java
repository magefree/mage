package mage.utils.timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import mage.MageException;
import mage.interfaces.Action;
import org.apache.log4j.Logger;

/**
 * Priority timer for both server and client sides
 * Client side version used for GUI-avatars redraw
 *
 * @author noxx
 */
public class PriorityTimer extends TimerTask {

    private static final Logger logger = Logger.getLogger(PriorityTimer.class);

    // timer must be cancelled explicitly, see cancel() below
    // required to avoid memory leaks after java 18
    private Timer timer;

    private final long delay;
    private final Action taskOnTimeout;

    private int count;
    private int bufferCount = 0;
    private Action taskOnTick;
    private States state = States.NONE;

    enum States {
        NONE,
        INIT,
        RUNNING,
        PAUSED,
        FINISHED
    }

    public PriorityTimer(int count, long delay, Action taskOnTimeout) {
        this.count = count;
        this.delay = delay;
        this.taskOnTimeout = taskOnTimeout;
    }

    public void init(UUID gameId) {
        if (this.timer != null) {
            this.timer.cancel();
        }
        state = States.INIT;
        long delayMs = delay * (int) (1000L / delay);
        this.timer = new Timer("Priority Timer-" + gameId.toString(), false);
        timer.scheduleAtFixedRate(this, delayMs, delayMs);
    }

    public void start() {
        if (state == States.NONE) {
            throw new IllegalStateException("Timer should have been initialized first");
        }
        if (state == States.FINISHED) {
            throw new IllegalStateException("Timer has already finished its work");
        }
        state = States.RUNNING;
    }

    public void pause() {
        state = States.PAUSED;
    }

    public void stop() {
        state = States.FINISHED;
        count = 0;
    }

    public void resume() {
        if (state == States.FINISHED) {
            throw new IllegalStateException("Timer has already finished its work");
        }
        state = States.RUNNING;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getBufferCount() {
        return bufferCount;
    }

    public void setBufferCount(int count) {
        this.bufferCount = count;
    }

    public void setTaskOnTick(Action taskOnTick) {
        this.taskOnTick = taskOnTick;
    }

    @Override
    public void run() {
        if (state == States.RUNNING) {
            // Count down buffer time first
            if (bufferCount > 0) {
                bufferCount--;
            } else {
                count--;
            }

            if (taskOnTick != null) {
                try {
                    taskOnTick.execute();
                } catch (MageException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Count is: " + count);
        }
        //System.out.println("Count is: " + count);
        if (count <= 0) {
            cancel();
            try {
                taskOnTimeout.execute();
            } catch (MageException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean cancel() {
        // PriorityTimer is a TimerTask, so inherited cancel() stops the TASK only - the Timer's
        // own thread keeps waiting on its queue. Until java 17 that thread was auto-stopped by
        // Timer's internal finalizer, but finalization is deprecated since JDK 18 (JEP 421) and
        // going away, so the timer must be cancelled explicitly now, exactly as Timer's javadoc
        // requires: "if a caller wants to terminate a timer's task execution thread rapidly,
        // the caller should invoke the timer's cancel method"
        boolean res = super.cancel();
        if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
        }
        return res;
    }

}
