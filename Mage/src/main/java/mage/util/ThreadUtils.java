package mage.util;

import com.google.common.base.Throwables;

import javax.swing.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Helper class to work with threads
 *
 * @author ayrat, JayDi85
 */
public final class ThreadUtils {

    // basic
    public final static String THREAD_PREFIX_GAME = "GAME";
    public final static String THREAD_PREFIX_AI_SIMULATION_MAD = "AI-SIM-MAD";
    public final static String THREAD_PREFIX_AI_SIMULATION_MCTS = "AI-SIM-MCTS";
    public final static String THREAD_PREFIX_CALL_REQUEST = "CALL";
    public final static String THREAD_PREFIX_TOURNEY = "TOURNEY";
    public final static String THREAD_PREFIX_TOURNEY_DRAFT = "TOURNEY DRAFT";
    public final static String THREAD_PREFIX_TOURNEY_BOOSTERS_SEND = "TOURNEY BOOSTERS SEND";

    // game
    public final static String THREAD_PREFIX_GAME_JOIN_WAITING = "XMAGE game join waiting";

    // services
    public final static String THREAD_PREFIX_SERVICE_HEALTH = "XMAGE service health";
    public final static String THREAD_PREFIX_SERVICE_USERS_LIST_REFRESH = "XMAGE users list refresh";
    public final static String THREAD_PREFIX_SERVICE_CONNECTION_EXPIRED_CHECK = "XMAGE connection expired check";
    public final static String THREAD_PREFIX_SERVICE_LOBBY_REFRESH = "XMAGE lobby refresh";
    public final static String THREAD_PREFIX_SERVICE_NEWS_REFRESH = "XMAGE news refresh";

    // etc
    public final static String THREAD_PREFIX_TIMEOUT = "XMAGE timeout";
    public final static String THREAD_PREFIX_TIMEOUT_IDLE = "XMAGE timeout_idle";

    // client
    // TODO: replace single GUI tasks by swing thread (invoke later) or by single executor like (like CALL for server side)
    public final static String THREAD_PREFIX_CLIENT_SYMBOLS_DOWNLOADER = "XMAGE symbols downloader";
    public final static String THREAD_PREFIX_CLIENT_IMAGES_DOWNLOADER = "XMAGE images downloader";
    public final static String THREAD_PREFIX_CLIENT_PING_SENDER = "XMAGE ping sender";
    public final static String THREAD_PREFIX_CLIENT_SUBMIT_TIMER = "XMAGE submit timer";
    public final static String THREAD_PREFIX_CLIENT_AUTO_CLOSE_TIMER = "XMAGE auto-close timer";

    // tests
    public final static String THREAD_PREFIX_TESTS_AI_VS_AI_GAMES = "XMAGE tests ai vs ai";

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public static void wait(Object lock) {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ex) {
            }
        }
    }

    /**
     * Find real exception object after thread task completed. Can be used in afterExecute
     */
    public static Throwable findRunnableException(Runnable r, Throwable t) {
        // executer.submit - return exception in result
        // executer.execute - return exception in t
        if (t == null && r instanceof Future<?>) {
            try {
                Object result = ((Future<?>) r).get();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
        return t;
    }

    public static Throwable findRootException(Throwable t) {
        return Throwables.getRootCause(t);
    }

    public static void ensureRunInGameThread() {
        if (!isRunGameThread()) {
            // for real games
            // how-to fix: use signal logic to inform a game about new command to execute instead direct execute (see example with WantConcede)
            // reason: user responses/commands are received by network/call thread, but must be processed by game thread
            //
            // for unit tests
            // how-to fix: if your test runner uses a diff thread name to run tests then add it to isRunGameThread
            throw new IllegalArgumentException("Wrong code usage: game related code must run in GAME thread, but it used in " + Thread.currentThread().getName(), new Throwable());
        }
    }

    public static boolean isRunGameThread() {
        String name = Thread.currentThread().getName();
        if (name.startsWith(THREAD_PREFIX_GAME)) {
            // server game
            return true;
        } else if (name.startsWith(THREAD_PREFIX_AI_SIMULATION_MAD)) {
            // ai simulation
            return true;
        } else if (name.equals("main")) {
            // unit test
            return true;
        } else {
            return false;
        }
    }

    public static void ensureRunInCallThread() {
        String name = Thread.currentThread().getName();
        if (!name.startsWith(THREAD_PREFIX_CALL_REQUEST)) {
            // how-to fix: something wrong in your code logic
            throw new IllegalArgumentException("Wrong code usage: client commands code must run in CALL threads, but used in " + name, new Throwable());
        }
    }

    public static void ensureRunInGUISwingThread() {
        if (!SwingUtilities.isEventDispatchThread()) {
            // hot-to fix: run GUI changeable code by SwingUtilities.invokeLater(() -> {xxx})
            throw new IllegalArgumentException("Wrong code usage: GUI related code must run in SWING thread by SwingUtilities.invokeLater", new Throwable());
        }
    }
}
