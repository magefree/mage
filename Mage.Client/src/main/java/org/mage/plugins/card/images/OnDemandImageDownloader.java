package org.mage.plugins.card.images;

import mage.client.dialog.PreferencesDialog;
import mage.client.remote.XmageURLConnection;
import mage.util.XmageThreadFactory;
import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileOutputStream;
import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.sources.CardImageSource;
import org.mage.plugins.card.dl.sources.CardImageUrls;
import org.mage.plugins.card.dl.sources.ScryfallImageSourceNormal;
import org.mage.plugins.card.utils.CardImageUtils;

import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;

/**
 * On-demand image downloader service.
 * Downloads missing card images automatically when they are first displayed.
 * Uses Scryfall as the image source with normal quality.
 *
 * @author Claude
 */
public class OnDemandImageDownloader {

    private static final Logger logger = Logger.getLogger(OnDemandImageDownloader.class);

    private static final OnDemandImageDownloader instance = new OnDemandImageDownloader();

    // Track cards that have been queued or downloaded to avoid duplicates
    private final Set<String> queuedOrDownloaded = ConcurrentHashMap.newKeySet();

    // Download queue
    private final BlockingQueue<CardDownloadData> downloadQueue = new LinkedBlockingQueue<>();

    // Single-threaded executor for downloads (to respect rate limits)
    private final ExecutorService executor;

    // Image source - use normal quality Scryfall
    private final CardImageSource imageSource = ScryfallImageSourceNormal.getInstance();

    // Minimum file size to consider download successful
    private static final int MIN_FILE_SIZE_OF_GOOD_IMAGE = 1024 * 6;

    private OnDemandImageDownloader() {
        executor = Executors.newSingleThreadExecutor(
                new XmageThreadFactory("OnDemandImageDownloader", true)
        );
        executor.submit(this::downloadWorker);
    }

    public static OnDemandImageDownloader getInstance() {
        return instance;
    }

    /**
     * Queue a card for download if not already queued/downloaded.
     * Only queues if auto-download is enabled in preferences.
     *
     * @param card The card to download
     */
    public void queueDownload(CardDownloadData card) {
        // Check if feature is enabled
        if (!PreferencesDialog.isAutoDownloadEnabled()) {
            return;
        }

        if (card == null) {
            return;
        }

        // Create unique key for this card
        String key = createKey(card);

        // Only queue if not already queued/downloaded
        if (queuedOrDownloaded.add(key)) {
            downloadQueue.offer(card);
            logger.debug("Queued for on-demand download: " + card.getName() + " (" + card.getSet() + ")");
        }
    }

    private String createKey(CardDownloadData card) {
        return card.getName() + "#" + card.getSet() + "#" + card.getCollectorId() + "#" + card.isToken();
    }

    /**
     * Background worker that processes the download queue
     */
    private void downloadWorker() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                CardDownloadData card = downloadQueue.take();
                downloadCard(card);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                logger.error("Error in download worker: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Download a single card image
     *
     * @param card The card to download
     * @return true if download was successful
     */
    private boolean downloadCard(CardDownloadData card) {
        TFile fileTempImage = null;
        TFile destFile = null;

        try {
            // Generate download URLs
            CardImageUrls urls;
            if (card.isToken()) {
                urls = imageSource.generateTokenUrl(card);
            } else {
                urls = imageSource.generateCardUrl(card);
            }

            if (urls == null) {
                logger.debug("No download URL available for: " + card.getName() + " (" + card.getSet() + ")");
                return false;
            }

            // Create temp file
            String tempPath = getImagesDir() + File.separator + "downloading" + File.separator;
            fileTempImage = new TFile(tempPath + CardImageUtils.prepareCardNameForFile(card.getName()) + "-" + card.hashCode() + ".jpg");
            TFile parentFile = fileTempImage.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                parentFile.mkdirs();
            }

            // Destination file
            destFile = new TFile(CardImageUtils.buildImagePathToCardOrToken(card));

            // Skip if file already exists (might have been downloaded by another means)
            if (destFile.exists() && destFile.length() >= MIN_FILE_SIZE_OF_GOOD_IMAGE) {
                logger.debug("Image already exists: " + card.getName() + " (" + card.getSet() + ")");
                return true;
            }

            // Try to download from available URLs
            List<String> downloadUrls = urls.getDownloadList();
            XmageURLConnection connection = null;
            boolean isDownloadOK = false;

            for (String currentUrl : downloadUrls) {
                // Rate limiting
                imageSource.doPause(currentUrl);

                connection = new XmageURLConnection(currentUrl);
                connection.startConnection();
                if (connection.isConnected()) {
                    connection.setRequestHeaders(imageSource.getHttpRequestHeaders(currentUrl));

                    try {
                        connection.connect();
                    } catch (SocketException | UnknownHostException e) {
                        logger.debug("Network error downloading " + card.getName() + ": " + e.getMessage());
                        continue;
                    }

                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        isDownloadOK = true;
                        break;
                    } else {
                        logger.debug("HTTP " + responseCode + " for " + card.getName() + " from " + currentUrl);
                    }
                }
            }

            // Save the downloaded file
            if (isDownloadOK && connection != null && connection.isConnected()) {
                try (InputStream in = new BufferedInputStream(connection.getGoodResponseAsStream());
                     OutputStream tempFileStream = new TFileOutputStream(fileTempImage);
                     OutputStream out = new BufferedOutputStream(tempFileStream)) {

                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) != -1) {
                        out.write(buf, 0, len);
                    }
                }

                // Validate and move to final destination
                if (fileTempImage.exists() && fileTempImage.length() >= MIN_FILE_SIZE_OF_GOOD_IMAGE) {
                    if (!destFile.getParentFile().exists()) {
                        destFile.getParentFile().mkdirs();
                    }
                    fileTempImage.cp_rp(destFile);
                    logger.info("Downloaded: " + card.getName() + " (" + card.getSet() + ")");
                    return true;
                } else {
                    logger.debug("Downloaded file too small for: " + card.getName());
                }
            }

        } catch (AccessDeniedException e) {
            logger.error("Access denied downloading " + card.getName() + ": " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error downloading " + card.getName() + ": " + e.getMessage(), e);
        } finally {
            // Cleanup temp file
            if (fileTempImage != null && fileTempImage.exists()) {
                try {
                    TFile.rm(fileTempImage);
                } catch (Exception e) {
                    logger.debug("Could not delete temp file: " + e.getMessage());
                }
            }
        }

        return false;
    }

    /**
     * Clear the tracking set (useful for testing or reset)
     */
    public void clearTracking() {
        queuedOrDownloaded.clear();
    }
}
