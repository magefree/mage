package mage.utils.timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import mage.MageException;
import mage.interfaces.Action;
import org.apache.log4j.Logger;

/**
 * @author noxx
 */
public class PriorityTimer extends TimerTask {

    private static final Logger logger = Logger.getLogger(PriorityTimer.class);

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
        state = States.INIT;
        Timer timer = new Timer("Priority Timer-" + gameId.toString(), false);
        long delayMs = delay * (int) (1000L / delay);
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

}
