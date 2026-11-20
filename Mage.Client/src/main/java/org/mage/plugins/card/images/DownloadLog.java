package org.mage.plugins.card.images;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Images download: detailed log of the last download (one line per image and per service step),
 * for debugging only, keeps the last run in the temp folder of the images storage
 * Path example: /Mage.Client/plugins/images/downloading/last-download.log
 *
 * @author JayDi85
 */
public class DownloadLog {

    private static final Logger logger = Logger.getLogger(DownloadLog.class);
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public static final String LOG_FILE_NAME = "last-download.log";

    private final File file;
    private PrintWriter writer;

    public DownloadLog(File folder) {
        this.file = new File(folder, LOG_FILE_NAME);
    }

    public synchronized void start() {
        close();
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            this.writer = new PrintWriter(new FileWriter(file, false));
        } catch (IOException e) {
            logger.error("Images: can't create download log " + file + ": " + e.getMessage(), e);
            this.writer = null;
        }
        add("download started");
    }

    public synchronized void add(String message) {
        if (writer == null) {
            return;
        }
        writer.println(LocalDateTime.now().format(TIME_FORMAT) + " [" + Thread.currentThread().getName() + "] " + message);
        writer.flush();
    }

    public synchronized void close() {
        if (writer != null) {
            add("download finished");
            writer.close();
            writer = null;
        }
    }

    public File getFile() {
        return file;
    }
}
