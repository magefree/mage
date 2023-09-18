package mage.client.util.audio;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

import org.apache.log4j.Logger;

import mage.utils.ThreadUtils;

public class LinePool {

    private final org.apache.log4j.Logger logger = Logger.getLogger(LinePool.class);
    private static final int LINE_CLEANUP_INTERVAL = 30000;

    private final Queue<SourceDataLine> freeLines = new ArrayDeque<>();
    private final Queue<SourceDataLine> activeLines = new ArrayDeque<>();
    private final Set<SourceDataLine> busyLines = new HashSet<>();
    private final LinkedList<MageClip> queue = new LinkedList<>();

    /*
     * Initially all the lines are in the freeLines pool. When a sound plays, one line is being selected randomly from
     * the activeLines and then, if it's empty, from the freeLines pool and used to play the sound. The line is moved to
     * busyLines. When a sound stops, the line is moved to activeLines if it contains <= elements than alwaysActive
     * parameter, else it's moved to the freeLines pool. Every 30 seconds the lines in the freeLines pool are closed
     * from the timer thread to prevent deadlocks in PulseAudio internals.
     */

    private final Mixer mixer;
    private final int alwaysActive;

    public LinePool() {
        this(new AudioFormat(22050, 16, 1, true, false), 4, 1);
    }

    public LinePool(AudioFormat audioFormat, int size, int alwaysActive) {
        this.alwaysActive = alwaysActive;
        mixer = AudioSystem.getMixer(null);
        DataLine.Info lineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
        for (int i = 0; i < size; i++) {
            try {
                SourceDataLine line = (SourceDataLine) mixer.getLine(lineInfo);
                freeLines.add(line);
            } catch (LineUnavailableException e) {
                logger.warn("Failed to get line from mixer", e);
            }
        }
        new Timer("Line cleanup", true).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (LinePool.this) {
                    for (SourceDataLine sourceDataLine : freeLines) {
                        if (sourceDataLine.isOpen()) {
                            sourceDataLine.close();
                            logger.debug("Closed line " + sourceDataLine);
                        }
                    }
                }
            }
        }, LINE_CLEANUP_INTERVAL, LINE_CLEANUP_INTERVAL);
    }

    private synchronized SourceDataLine borrowLine() {
        SourceDataLine line = activeLines.poll();
        if (line == null) {
            line = freeLines.poll();
        }
        if (line != null) {
            busyLines.add(line);
        }
        return line;
    }

    private synchronized void returnLine(SourceDataLine line) {
        busyLines.remove(line);
        if (activeLines.size() < alwaysActive) {
            activeLines.add(line);
        } else {
            freeLines.add(line);
        }
    }

    public void playSound(final MageClip mageClip) {
        final SourceDataLine line;
        synchronized (LinePool.this) {
            logger.debug("Playing: " + mageClip.getFilename());
            logLineStats();
            line = borrowLine();
            if (line == null) {
                // no lines available, queue sound to play it when a line is available
                queue.add(mageClip);
                logger.debug("Sound queued: " + mageClip.getFilename());
                return;
            }
            logLineStats();
        }
        ThreadUtils.threadPool.submit(() -> {
            synchronized (LinePool.this) {
                try {
                    if (!line.isOpen()) {
                        line.open();
                        line.addLineListener(event -> {
                            logger.debug("Event: " + event);
                            if (event.getType() != Type.STOP) {
                                return;
                            }
                            synchronized (LinePool.this) {
                                logger.debug("Before stop on line " + line);
                                logLineStats();
                                returnLine(line);
                                logger.debug("After stop on line " + line);
                                logLineStats();
                                MageClip queuedSound = queue.poll();
                                if (queuedSound != null) {
                                    logger.debug("Playing queued sound " + queuedSound);
                                    playSound(queuedSound);
                                }
                            }
                        });
                    }
                    line.start();
                } catch (LineUnavailableException e) {
                    logger.warn("Failed to open line", e);
                }
            }
            byte[] buffer = mageClip.getBuffer();
            logger.debug("Before write to line " + line);
            line.write(buffer, 0, buffer.length);
            line.drain();
            line.stop();
            logger.debug("Line completed: " + line);
        });
    }

    private void logLineStats() {
        logger.debug(String.format("Free lines: %d; Active: %d; Busy: %d",
                freeLines.size(), activeLines.size(), busyLines.size()
        ));
    }
}
