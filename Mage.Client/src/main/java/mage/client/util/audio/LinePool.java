package mage.client.util.audio;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinePool {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final int LINE_CLEANUP_INTERVAL = 30000;
    AudioFormat format;
    Set<SourceDataLine> freeLines = new HashSet<>();
    Set<SourceDataLine> activeLines = new HashSet<>();
    Set<SourceDataLine> busyLines = new HashSet<>();
    LinkedList<MageClip> queue = new LinkedList<>();

    /*
     * Initially all the lines are in the freeLines pool. When a sound plays, one line is being selected randomly from
     * the activeLines and then, if it's empty, from the freeLines pool and used to play the sound. The line is moved to
     * busyLines. When a sound stops, the line is moved to activeLines if it contains <= elements than alwaysActive
     * parameter, else it's moved to the freeLines pool. Every 30 seconds the lines in the freeLines pool are closed
     * from the timer thread to prevent deadlocks in PulseAudio internals.
     */

    private Mixer mixer;
    private int alwaysActive;
    private ThreadPoolExecutor threadPool;
    private int threadCount;

    public LinePool() {
        this(new AudioFormat(22050, 16, 1, true, false), 4, 1);
    }

    public LinePool(AudioFormat audioFormat, int size, int alwaysActive) {
        threadPool = new ThreadPoolExecutor(alwaysActive, size, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactory() {
            @Override
            public Thread newThread (Runnable runnable) {
                threadCount++;
                Thread thread = new Thread(runnable, "Audio" + threadCount);
                thread.setDaemon(true);
                return thread;
            }
        });
        threadPool.prestartAllCoreThreads();

        format = audioFormat;
        this.alwaysActive = alwaysActive;
        mixer = AudioSystem.getMixer(null);
        DataLine.Info lineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
        for (int i = 0; i < size; i++) {
            try {
                SourceDataLine line = (SourceDataLine) mixer.getLine(lineInfo);
                freeLines.add(line);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
        new Timer("Line cleanup", true).scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                synchronized (LinePool.this) {
                    for (SourceDataLine sourceDataLine : freeLines) {
                        if (sourceDataLine.isOpen()) {
                            sourceDataLine.close();
                            log.debug("Closed line {}", sourceDataLine);
                        }
                    }
                }
            }
        }, LINE_CLEANUP_INTERVAL, LINE_CLEANUP_INTERVAL);
    }

    public void playSound(final MageClip mageClip) {
        final SourceDataLine line;
        synchronized (LinePool.this) {
            log.debug("Playing {}", mageClip.getFilename());
            logLineStats();
            if (activeLines.size() > 0) {
                line = activeLines.iterator().next();
            } else if (freeLines.size() > 0) {
                line = freeLines.iterator().next();
            } else {
                // no lines available, queue sound to play it when a line is available
                queue.add(mageClip);
                log.debug("Sound {} queued.", mageClip.getFilename());
                return;
            }
            freeLines.remove(line);
            activeLines.remove(line);
            busyLines.add(line);
            logLineStats();
        }
        threadPool.submit(new Runnable() {

            @Override
            public void run() {
                synchronized (LinePool.this) {
                    try {
                        if (!line.isOpen()) {
                            line.open();
                            line.addLineListener(new LineListener() {

                                @Override
                                public void update(LineEvent event) {
                                    log.debug("Event: {}", event);
                                    if (event.getType() != Type.STOP) {
                                        return;
                                    }
                                    synchronized (LinePool.this) {
                                        log.debug("Before stop on line {}", line);
                                        logLineStats();
                                        busyLines.remove(line);
                                        if (activeLines.size() < LinePool.this.alwaysActive) {
                                            activeLines.add(line);
                                        } else {
                                            freeLines.add(line);
                                        }
                                        log.debug("After stop on line {}", line);
                                        logLineStats();
                                        if (queue.size() > 0) {
                                            MageClip queuedSound = queue.poll();
                                            log.debug("Playing queued sound {}", queuedSound);
                                            playSound(queuedSound);
                                        }
                                    }
                                }
                            });
                        }
                        line.start();
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    }
                }
                byte[] buffer = mageClip.getBuffer();
                log.debug("Before write to line {}", line);
                line.write(buffer, 0, buffer.length);
                line.drain();
                line.stop();
                log.debug("Line completed: {}", line);
            }
        });
    }

    private void logLineStats() {
        log.debug("Free lines: {} Active: {} Busy: {}", freeLines.size(), activeLines.size(), busyLines.size());
    }
}
