
package mage.server.util;

import mage.utils.StreamUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Handles server messages (Messages of the Day). Reloads messages every 5
 * minutes.
 *
 * @author nantuko
 */
public enum ServerMessagesUtil {
    instance;
    private static final Logger log = Logger.getLogger(ServerMessagesUtil.class);
    private static final String SERVER_MSG_TXT_FILE = "server.msg.txt";
    private ScheduledExecutorService updateExecutor;

    private final List<String> messages = new ArrayList<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private static String pathToExternalMessages = null;

    private static boolean ignore = false;

    private static long startDate;
    private static final AtomicInteger gamesStarted = new AtomicInteger(0);
    private static final AtomicInteger tournamentsStarted = new AtomicInteger(0);
    private static final AtomicInteger lostConnection = new AtomicInteger(0);
    private static final AtomicInteger reconnects = new AtomicInteger(0);

    static {
        pathToExternalMessages = System.getProperty("messagesPath");
    }

    ServerMessagesUtil() {
        updateExecutor = Executors.newSingleThreadScheduledExecutor();
        updateExecutor.scheduleAtFixedRate(this::reloadMessages, 5, 5 * 60, TimeUnit.SECONDS);
    }


    public List<String> getMessages() {
        lock.readLock().lock();
        try {
            return messages;
        } finally {
            lock.readLock().unlock();
        }
    }

    private void reloadMessages() {
        log.debug("Reading server messages...");
        List<String> motdMessages = readFromFile();
        List<String> newMessages = new ArrayList<>();
        newMessages.addAll(motdMessages);
        newMessages.add(getServerStatistics());
        newMessages.add(getServerStatistics2());

        lock.writeLock().lock();
        try {
            messages.clear();
            messages.addAll(newMessages);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private List<String> readFromFile() {
        if (ignore) {
            return Collections.emptyList();
        }
        File externalFile = null;
        if (pathToExternalMessages != null) {
            externalFile = new File(pathToExternalMessages);
            if (!externalFile.exists()) {
                log.warn("Couldn't find server.msg.txt using external path: " + pathToExternalMessages);
                pathToExternalMessages = null; // not to repeat error action again
                externalFile = null;
            } else if (!externalFile.canRead()) {
                log.warn("Couldn't read (no access) server.msg.txt using external path: " + pathToExternalMessages);
                pathToExternalMessages = null; // not to repeat error action again
            }
        }
        InputStream is = null;
        if (externalFile != null) {
            try {
                is = new FileInputStream(externalFile);
            } catch (Exception f) {
                log.error(f, f);
                pathToExternalMessages = null; // not to repeat error action again
            }
        } else {
            File file = new File(SERVER_MSG_TXT_FILE);
            if (!file.exists() || !file.canRead()) {
                log.warn("Couldn't find server.msg.txt using path: " + SERVER_MSG_TXT_FILE);
            } else {
                try {
                    is = new FileInputStream(file);
                } catch (Exception f) {
                    log.error(f, f);
                    ignore = true;
                }
            }
        }
        if (is == null) {
            log.warn("Couldn't find server.msg");
            return Collections.emptyList();
        }

        List<String> newMessages = new ArrayList<>();
        try(Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                if (!message.trim().isEmpty()) {
                    newMessages.add(message.trim());
                }
            }
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            StreamUtils.closeQuietly(is);
        }
        return newMessages;
    }

    private String getServerStatistics() {
        long current = System.currentTimeMillis();
        long hours = ((current - startDate) / (1000 * 60 * 60));
        StringBuilder statistics = new StringBuilder("Server uptime: ");
        statistics.append(hours);
        statistics.append(" hour(s), games played: ");
        statistics.append(gamesStarted.get());
        statistics.append(" tournaments started: ");
        statistics.append(tournamentsStarted.get());
        return statistics.toString();
    }

    private String getServerStatistics2() {
        long current = System.currentTimeMillis();
        long minutes = ((current - startDate) / (1000 * 60));
        if (minutes == 0) {
            minutes = 1;
        }
        StringBuilder statistics = new StringBuilder("Disconnects: ");
        statistics.append(lostConnection.get());
        statistics.append(" avg/hour ").append(lostConnection.get() * 60 / minutes);
        statistics.append(" Reconnects: ").append(reconnects.get());
        statistics.append(" avg/hour ").append(reconnects.get() * 60 / minutes);
        return statistics.toString();
    }

    //    private Timer timer = new Timer(1000 * 60, new ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//            reloadMessages();
//        }
//    });
    public void setStartDate(long milliseconds) {
        startDate = milliseconds;
    }

    public void incGamesStarted() {
        int value;
        do {
            value = gamesStarted.get();
        } while (!gamesStarted.compareAndSet(value, value + 1));
    }

    public void incTournamentsStarted() {
        int value;
        do {
            value = tournamentsStarted.get();
        } while (!tournamentsStarted.compareAndSet(value, value + 1));
    }

    public void incReconnects() {
        int value;
        do {
            value = reconnects.get();
        } while (!reconnects.compareAndSet(value, value + 1));
    }

    public void incLostConnection() {
        int value;
        do {
            value = lostConnection.get();
        } while (!lostConnection.compareAndSet(value, value + 1));
    }

}
