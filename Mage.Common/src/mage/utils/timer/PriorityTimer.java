package mage.utils.timer;

import mage.MageException;
import mage.interfaces.Action;
import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author noxx
 */
public class PriorityTimer extends TimerTask {

    private static final Logger logger = Logger.getLogger(PriorityTimer.class);

    private int count;

    private long delay;

    private Action taskOnTimeout;

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

    public void init() {
        state = States.INIT;
        Timer timer = new Timer("Priority Timer", false);
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

    public void setTaskOnTick(Action taskOnTick) {
        this.taskOnTick = taskOnTick;
    }

    @Override
    public void run() {
        if (state == States.RUNNING) {
            count--;
            if (taskOnTick != null) {
                try {
                    taskOnTick.execute();
                } catch (MageException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (logger.isDebugEnabled()) logger.debug("Count is: " + count);
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

    public static void main(String[] args) throws Exception {
        long delay = 250L;
        int count = 5;
        PriorityTimer timer = new PriorityTimer(count, delay, new Action() {
            @Override
            public void execute() throws MageException {
                System.out.println("Exit");
                System.exit(0);
            }
        });
        timer.init();
        timer.start();
        Thread.sleep(2000);
        timer.pause();
        Thread.sleep(3000);
        timer.resume();
    }
}
